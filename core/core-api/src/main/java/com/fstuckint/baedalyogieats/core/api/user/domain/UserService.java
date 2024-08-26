package com.fstuckint.baedalyogieats.core.api.user.domain;

import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.LoginDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.SignupDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.UserInfoDto;
import com.fstuckint.baedalyogieats.core.api.user.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.error.UserException;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.storage.db.core.user.User;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRepository;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public ResponseEntity<ApiResponse<?>> signUp(SignupDto signupDto) {
        User usernameCheck = userRepository.findByUsername(signupDto.getUsername()).orElse(null);
        if (usernameCheck != null) throw new UserException(ErrorType.USERNAME_PASSWORD_BAD_REQUEST_ERROR);
        User nicknameCheck = userRepository.findByNickname(signupDto.getNickname()).orElse(null);
        if (nicknameCheck != null) throw new UserException(ErrorType.DUPLICATE_NICKNAME_ERROR);

        validationRole(signupDto);
        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());

        userRepository.save(new User(signupDto.getUsername(), encodedPassword, signupDto.getNickname(), signupDto.getUserRole()));
        return ResponseEntity.ok(ApiResponse.success());
    }


//    @Transactional(readOnly = true)
//    public ResponseEntity<ApiResponse<?>> login(LoginDto loginDto) {
//        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new UserException(ErrorType.USERNAME_PASSWORD_BAD_REQUEST_ERROR));
//        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
//            throw new UserException(ErrorType.USERNAME_PASSWORD_BAD_REQUEST_ERROR);
//
//        String token = jwtUtils.createToken(user.getUsername(), user.getRole());
//        return ResponseEntity.ok().header(AUTHORIZATION_HEADER, token).body(ApiResponse.success(token));
//    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> getUserList(String token, LocalDateTime cursor, Pageable pageable, String sortKey, String direction) {
        jwtUtils.CHECK_ADMIN(token);
        if (sortKey.isEmpty()) sortKey = "createdAt";
        if (direction.isEmpty()) direction = "ASC";

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortKey);
        PageRequest sortedPage = PageRequest.of(0, pageable.getPageSize(), sort);

        Page<User> pageList = userRepository.findAllByCursor(cursor != null ? cursor : LocalDateTime.of(2000, 1, 1, 0, 0), sortedPage);
        List<UserInfoDto> dtoList = pageList.stream().map(UserInfoDto::new).toList();

        //TODO: ApiResponse data 에 ... list 하고 hasNext 어떻게 같이 넣을까.. ( ObjectMapper 써야하나..? )
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(ErrorType.NOT_FOUND_ERROR));
        return ResponseEntity.ok(ApiResponse.success(new UserInfoDto(user)));
    }

    private void validationRole(SignupDto signupDto) {
        UserRole userRoleFromDto = signupDto.getUserRole();
        boolean isValidRole = Arrays.stream(UserRole.values()).anyMatch(role -> role == userRoleFromDto);
        if (!isValidRole) throw new UserException(ErrorType.NOT_MATCH_ROLE_ERROR);
    }
}
