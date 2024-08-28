package com.fstuckint.baedalyogieats.core.api.address.domain;

import com.fstuckint.baedalyogieats.core.api.address.controller.v1.request.AddressRequest;
import com.fstuckint.baedalyogieats.core.api.address.support.error.AddressException;
import com.fstuckint.baedalyogieats.core.api.address.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.storage.db.core.address.AddressEntity;
import com.fstuckint.baedalyogieats.storage.db.core.address.AddressRepository;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserEntity;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddressPilot {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    public AddressResult registerAddress(String token, AddressRequest addressRequest) {
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        UserEntity useEntity = userRepository.findByUuidAndIsDeletedFalse(userUuid)
                .orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        Address address = addressRequest.toAddress();
        if (useEntity.getUuid() != addressRequest.userUuid())
            throw new AddressException(ErrorType.BAD_REQUEST_ERROR);
        AddressEntity addressEntity = addressRepository.save(address.toEntity());
        return AddressResult.of(addressEntity);
    }

    public List<AddressResult> getAddressListByUser(String token, UUID userUuid) {
        UUID uuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (uuid != userUuid)
            throw new AddressException(ErrorType.TOKEN_ERROR);
        return addressRepository.findByUserUuidAndIsDeletedFalse(uuid).stream().map(AddressResult::of).toList();
    }

    public List<AddressResult> getAddressListByAdmin() {
        return addressRepository.findAll().stream().map(AddressResult::of).toList();
    }

    public AddressResult updateAddress(UUID addressUuid, AddressRequest addressRequest) {
        AddressEntity addressEntity = addressRepository.findByUuidAndIsDeletedFalse(addressUuid)
                .orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        return AddressResult.of(addressRepository.save(addressEntity.updateAddress(addressRequest.address())));
    }

    public void checkIdentity(String token, UUID addressId) {
        AddressEntity addressEntity = addressRepository.findByUuidAndIsDeletedFalse(addressId)
                .orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (addressEntity.getUserUuid() != userUuid)
            throw new AddressException(ErrorType.TOKEN_ERROR);
    }

    public void deleteAddress(UUID addressId) {
        AddressEntity addressEntity = addressRepository.findByUuidAndIsDeletedFalse(addressId)
                .orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        addressEntity.deleteAddress();
    }

}
