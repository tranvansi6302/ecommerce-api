package com.tranvansi.ecommerce.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tranvansi.ecommerce.dtos.requests.IntrospectRequest;
import com.tranvansi.ecommerce.dtos.requests.LoginRequest;
import com.tranvansi.ecommerce.dtos.requests.RegisterRequest;
import com.tranvansi.ecommerce.dtos.requests.VerifyTokenRequest;
import com.tranvansi.ecommerce.dtos.responses.IntrospectResponse;
import com.tranvansi.ecommerce.dtos.responses.LoginResponse;
import com.tranvansi.ecommerce.dtos.responses.RegisterResponse;
import com.tranvansi.ecommerce.entities.User;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.exceptions.ErrorCode;
import com.tranvansi.ecommerce.mappers.UserMapper;
import com.tranvansi.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userMapper.toUser(request);
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
    public SignedJWT verifyToken(VerifyTokenRequest request) throws ParseException, JOSEException {
        JWSVerifier jwsVerifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(request.getToken());

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(jwsVerifier);
        if (!verified || !expirationTime.after(new Date())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
//        if (logoutTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
//            throw new AppException(ErrorCode.UNAUTHORIZED);
//        }

        return signedJWT;
    }
}
