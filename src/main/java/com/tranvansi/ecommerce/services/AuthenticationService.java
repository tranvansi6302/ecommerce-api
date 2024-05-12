package com.tranvansi.ecommerce.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tranvansi.ecommerce.dtos.requests.*;
import com.tranvansi.ecommerce.dtos.responses.IntrospectResponse;
import com.tranvansi.ecommerce.dtos.responses.LoginResponse;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;
import com.tranvansi.ecommerce.entities.ForgotToken;
import com.tranvansi.ecommerce.entities.Role;
import com.tranvansi.ecommerce.entities.User;
import com.tranvansi.ecommerce.structures.MailStructure;
import com.tranvansi.ecommerce.enums.RoleName;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.enums.ErrorCode;
import com.tranvansi.ecommerce.mappers.ForgotTokenMapper;
import com.tranvansi.ecommerce.mappers.UserMapper;
import com.tranvansi.ecommerce.repositories.ForgotTokenRepository;
import com.tranvansi.ecommerce.repositories.RoleRepository;
import com.tranvansi.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService{
    private final UserRepository userRepository;
    private final ForgotTokenRepository forgotTokenRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final ForgotTokenMapper forgotTokenMapper;

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<Role> roles = roleRepository.findAllById(Collections.singleton(RoleName.USER.name()));
        User user = userMapper.toUser(request);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBlocked(false);
        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_OR_PASSWORD_INCORRECT));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.EMAIL_OR_PASSWORD_INCORRECT);
        }
        return LoginResponse.builder()
                .token(generateToken(user))
                .user(userMapper.toUserResponse(user))
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("ecommerce-api")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(3, ChronoUnit.DAYS))) // 3 day
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(
                header,
                payload
        );
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing JWT token", e);
            throw new RuntimeException(e);
        }

    }



    @Override
    public IntrospectResponse introspect(IntrospectRequest request)   throws ParseException, JOSEException {
        String token = request.getToken();
        boolean isTokenValid = true;
        VerifyTokenRequest verifyTokenRequest = VerifyTokenRequest.builder()
                .token(token)
                .build();
        try {
            verifyToken(verifyTokenRequest);
        } catch (AppException e) {
            isTokenValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isTokenValid)
                .build();
    }

    @Override
    public void verifyToken(VerifyTokenRequest request) throws ParseException, JOSEException {
        JWSVerifier jwsVerifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(request.getToken());

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(jwsVerifier);
        if (!verified || !expirationTime.after(new Date())) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
//        if (logoutTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
//            throw new AppException(ErrorCode.UNAUTHORIZED);
//        }

    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> stringJoiner.add(role.getName()));
        }
        return stringJoiner.toString();
    }


    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        String token = generatePasswordResetToken(user);
        ForgotToken forgotToken = forgotTokenMapper.toForgotToken(request);
        forgotToken.setToken(token);
        forgotTokenRepository.save(forgotToken);
        // send email to user, ** Refactor later **
        String htmlContent = "Khôi phục mật khẩu vui lòng nhấn link bên dưới (** Điều chỉnh template sau **)" +
                "\nhttp://localhost:6302/reset-password?token=" + token + "\n" +"Trân trọng!";
        MailStructure mailStructure = MailStructure.builder()
                .to(user.getEmail())
                .subject("Khôi phục mật khẩu")
                .content(htmlContent)
                .build();
        mailService.sendMail(mailStructure);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        ForgotToken forgotToken = forgotTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));
        try {
            verifyToken(VerifyTokenRequest.builder().token(request.getToken()).build());
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        User user = userRepository.findByEmail(forgotToken.getEmail()).orElseThrow(
                () -> new AppException(ErrorCode.EMAIL_NOT_FOUND)
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        forgotTokenRepository.delete(forgotToken);
    }


    private String generatePasswordResetToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("ecommerce-api")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS))) // 1 hour
                .jwtID(UUID.randomUUID().toString())
                .claim("purpose", "password_reset") // new claim
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(
                header,
                payload
        );
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing JWT token", e);
            throw new RuntimeException(e);
        }
    }
}

