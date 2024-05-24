package com.tranvansi.ecommerce.modules.addresses.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.enums.ErrorCode;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.addresses.entities.Address;
import com.tranvansi.ecommerce.modules.addresses.mappers.AddressMapper;
import com.tranvansi.ecommerce.modules.addresses.repositories.AddressRepository;
import com.tranvansi.ecommerce.modules.addresses.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.addresses.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.addresses.responses.AddressResponse;
import com.tranvansi.ecommerce.modules.users.entities.User;
import com.tranvansi.ecommerce.modules.users.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressResponse createAddress(CreateAddressRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Address address = addressMapper.createAddress(request);
        address.setUser(user);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse updateAddressDefault(Integer id, UpdateAddressDefaultRequest request) {

        Address address =
                addressRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        addressMapper.updateAddressDefault(address, request);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }
}
