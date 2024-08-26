package com.fstuckint.baedalyogieats.core.api.address.domain;

import com.fstuckint.baedalyogieats.core.api.address.controller.v1.request.AddressDto;
import com.fstuckint.baedalyogieats.core.api.address.controller.v1.response.AddressResponseDto;
import com.fstuckint.baedalyogieats.core.api.address.support.error.AddressException;
import com.fstuckint.baedalyogieats.core.api.address.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.storage.db.core.address.Address;
import com.fstuckint.baedalyogieats.storage.db.core.address.AddressRepository;
import com.fstuckint.baedalyogieats.storage.db.core.user.User;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRepository;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Transactional
    public ResponseEntity<ApiResponse<?>> registerAddress(AddressDto addressDto, HttpServletRequest request) {
        String username = extractUsername(request);
        userRepository.findByUsername(username).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));

        addressRepository.save(new Address(addressDto.getAddress(), username));
        return ResponseEntity.ok(ApiResponse.success(addressDto));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> getAddressList(HttpServletRequest request) {
        String username = extractUsername(request);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        if (!user.getUsername().equals(username)) throw new AddressException(ErrorType.TOKEN_ERROR);

        List<AddressResponseDto> list = addressRepository.findAllByUsername(username).stream()
                .map(AddressResponseDto::new)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> updateAddress(UUID addressId, AddressDto addressDto, HttpServletRequest request) {
        String username = extractUsername(request);
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        if (!address.getUsername().equals(username)) throw new AddressException(ErrorType.TOKEN_ERROR);

        addressRepository.save(address.updateAddress(addressDto.getAddress()));
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> deleteAddress(UUID addressId, HttpServletRequest request) {
        String username = extractUsername(request);
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressException(ErrorType.NOT_FOUND_ERROR));
        if (!address.getUsername().equals(username)) throw new AddressException(ErrorType.TOKEN_ERROR);

        // 추후 논리적 삭제로 변경
        addressRepository.delete(address);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> getAdminList(HttpServletRequest request) {
        String role = extractRole(request);
        if (UserRole.CUSTOMER.getAuthority().equals(role) || UserRole.OWNER.getAuthority().equals(role))
            throw new AddressException(ErrorType.ROLE_ERROR);

        return ResponseEntity.ok(ApiResponse.success(addressRepository.findAll().stream().map(AddressResponseDto::new).toList()));
    }


    private String extractUsername(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        log.debug("token: {}", token);
        if (token == null || !jwtUtils.validationToken(token)) throw new AddressException(ErrorType.TOKEN_ERROR);
        return jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_USERNAME).toString();
    }

    private String extractRole(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (token == null || !jwtUtils.validationToken(token)) throw new AddressException(ErrorType.TOKEN_ERROR);
        return jwtUtils.extractClaims(token).get(JwtUtils.CLAIMS_ROLE).toString();
    }


}
