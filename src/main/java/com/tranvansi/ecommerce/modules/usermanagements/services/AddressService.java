package com.tranvansi.ecommerce.modules.usermanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.utils.AuthUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.usermanagements.entities.Address;
import com.tranvansi.ecommerce.modules.usermanagements.mappers.AddressMapper;
import com.tranvansi.ecommerce.modules.usermanagements.repositories.AddressRepository;
import com.tranvansi.ecommerce.modules.usermanagements.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.UpdateAddressDefaultRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.AddressResponse;
import com.tranvansi.ecommerce.modules.usermanagements.services.interfaces.IAddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AuthUtil authUtil;

    @Override
    public AddressResponse createAddress(CreateAddressRequest request) {
        Address address = addressMapper.createAddress(request);
        address.setUser(authUtil.getUser());
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    @Override
    public Page<AddressResponse> getMyAddress(PageRequest pageRequest) {
        Page<Address> addresses = addressRepository.findByUserId(authUtil.getUser().getId(), pageRequest);
        return addresses.map(addressMapper::toAddressResponse);
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
