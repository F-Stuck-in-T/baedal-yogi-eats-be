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

    public AddressResult registerAddress(String bearerToken, AddressRequest addressRequest) {
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        UserEntity useEntity = userRepository.findByUuidAndIsDeletedFalse(userUuid)
            .orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        Address address = addressRequest.toAddress();
        if (useEntity.getUuid() != address.getUserUuid())
            throw new AddressException(ErrorType.BAD_REQUEST_ERROR);
        return AddressResult.of(addressRepository.save(address.toEntity()));
    }

    public AddressResult registerAddressAdmin(AddressRequest addressRequest) {
        return AddressResult.of(addressRepository.save(addressRequest.toAddress().toEntity()));
    }

    public List<AddressResult> getAddressListByUser(UUID userUuid, String bearerToken) {
        String token = jwtUtils.subStringToken(bearerToken);
        UUID uuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        if (uuid != userUuid)
            throw new AddressException(ErrorType.TOKEN_ERROR);
        return addressRepository.findAllByUserUuidAndIsDeletedFalse(uuid).stream().map(AddressResult::of).toList();
    }

    public List<AddressResult> getAddressListByUserAdmin(UUID userUuid) {
        return addressRepository.findAllByUserUuidAndIsDeletedFalse(userUuid).stream().map(AddressResult::of).toList();
    }

    public List<AddressResult> getAllAddressAdmin() {
        return addressRepository.findAllByIsDeletedFalse().stream().map(AddressResult::of).toList();
    }

    public AddressResult updateAddress(UUID addressUuid, AddressRequest addressRequest, String bearerToken) {
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        userRepository.findByUuidAndIsDeletedFalse(addressRequest.userUuid()).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        AddressEntity addressEntity = addressRepository.findByUuidAndIsDeletedFalse(addressUuid).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        if (!addressEntity.getUserUuid().equals(userUuid)) throw new AddressException(ErrorType.BAD_REQUEST_ERROR);
        return AddressResult.of(addressRepository.save(addressEntity.updateAddress(addressRequest.address())));
    }

    public AddressResult updateAddressAdmin(UUID addressId, AddressRequest addressRequest) {
        AddressEntity addressEntity = addressRepository.findByUuidAndIsDeletedFalse(addressId).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        return AddressResult.of(addressRepository.save(addressEntity.updateAddress(addressRequest.address())));
    }

    public AddressResult deleteAddress(UUID addressId, String bearerToken) {
        String token = jwtUtils.subStringToken(bearerToken);
        UUID userUuid = UUID.fromString(jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_UUID).toString());
        AddressEntity addressEntity = addressRepository.findByUuidAndIsDeletedFalse(addressId).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        if (!addressEntity.getUserUuid().equals(userUuid)) throw new AddressException(ErrorType.BAD_REQUEST_ERROR);
        return AddressResult.of(addressEntity.delete());
    }

    public AddressResult deleteAddressAdmin(UUID addressUuid) {
        AddressEntity addressEntity = addressRepository.findByUuidAndIsDeletedFalse(addressUuid).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        return AddressResult.of(addressEntity.delete());
    }

}
