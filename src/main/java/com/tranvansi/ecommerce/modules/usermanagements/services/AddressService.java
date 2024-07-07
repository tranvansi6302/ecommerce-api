package com.tranvansi.ecommerce.modules.usermanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.ErrorCode;
import com.tranvansi.ecommerce.components.utils.AuthUtil;
import com.tranvansi.ecommerce.exceptions.AppException;
import com.tranvansi.ecommerce.modules.usermanagements.entities.Address;
import com.tranvansi.ecommerce.modules.usermanagements.entities.User;
import com.tranvansi.ecommerce.modules.usermanagements.mappers.AddressMapper;
import com.tranvansi.ecommerce.modules.usermanagements.repositories.AddressRepository;
import com.tranvansi.ecommerce.modules.usermanagements.requests.CreateAddressRequest;
import com.tranvansi.ecommerce.modules.usermanagements.requests.DeleteAddressRequest;
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
        var isDefault = addressRepository.existsByUserIdAndIsDefault(authUtil.getUser().getId(), 1);
        Address address = addressMapper.createAddress(request);

        if (!isDefault) {
            address.setIsDefault(1);
        }
        address.setUser(authUtil.getUser());
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    @Override
    public Page<AddressResponse> getMyAddress(PageRequest pageRequest) {
        Page<Address> addresses =
                addressRepository.findByUserId(authUtil.getUser().getId(), pageRequest);
        return addresses.map(addressMapper::toAddressResponse);
    }

    @Override
    public AddressResponse updateAddressDefault(Integer id, UpdateAddressDefaultRequest request) {

        Address address =
                addressRepository
                        .findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var existDefaultAddress =
                addressRepository.findByIsDefaultAndUserId(1, authUtil.getUser().getId());
        if (existDefaultAddress.isPresent()) {
            existDefaultAddress.get().setIsDefault(0);
            addressRepository.save(existDefaultAddress.get());
        }
        addressMapper.updateAddressDefault(address, request);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(DeleteAddressRequest request) {
        User user = authUtil.getUser();
        if (!addressRepository.existsByUserIdAndId(user.getId(), request.getAddressId())) {
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
        }
        Address address =
                addressRepository
                        .findById(request.getAddressId())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        addressRepository.delete(address);
    }
}
