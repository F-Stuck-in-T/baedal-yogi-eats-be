package com.fstuckint.baedalyogieats.core.api.user.domain;

import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.LoginDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.SignupDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.UpdateUserDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.UserInfoDto;
import com.fstuckint.baedalyogieats.core.api.user.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.error.UserException;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.storage.db.core.user.User;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRepository;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import jakarta.servlet.http.HttpServletRequest;
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

        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        userRepository.save(new User(signupDto.getUsername(), encodedPassword, signupDto.getNickname(), signupDto.getUserRole()));
        return ResponseEntity.ok(ApiResponse.success());
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new UserException(ErrorType.USERNAME_PASSWORD_BAD_REQUEST_ERROR));
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new UserException(ErrorType.USERNAME_PASSWORD_BAD_REQUEST_ERROR);

        String token = jwtUtils.createToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok().header(JwtUtils.AUTHORIZATION_HEADER, token).body(ApiResponse.success(token));
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> getUserList(LocalDateTime cursor, Pageable pageable, String sortKey, String direction, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (!jwtUtils.validationToken(token)) throw new UserException(ErrorType.TOKEN_ERROR);
        if (!jwtUtils.checkRoleAdmin(token)) throw new UserException(ErrorType.NOT_ACCEPTABLE_ROLE_ERROR);

        if (sortKey == null) sortKey = "createdAt";
        if (direction == null) direction = "ASC";

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortKey);
        PageRequest sortedPage = PageRequest.of(0, pageable.getPageSize(), sort);

        Page<User> pageList = userRepository.findAllByCursor(cursor != null ? cursor : LocalDateTime.of(2000, 1, 1, 0, 0), sortedPage);
        List<UserInfoDto> dtoList = pageList.stream().map(UserInfoDto::new).toList();

        //TODO: ApiResponse data 에 ... list 하고 hasNext 어떻게 같이 넣을까.. ( ObjectMapper 써야하나..? )
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(ErrorType.NOT_FOUND_USER_ERROR));
        return ResponseEntity.ok(ApiResponse.success(new UserInfoDto(user)));
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(ErrorType.NOT_FOUND_USER_ERROR));

        User checkNickname = userRepository.findByNickname(updateUserDto.getNickname()).orElse(null);
        if (checkNickname != null) throw new UserException(ErrorType.DUPLICATE_NICKNAME_ERROR);

        user.updateUserInfo(passwordEncoder.encode(updateUserDto.getPassword()), updateUserDto.getNickname());
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.success(new UserInfoDto(user)));
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(ErrorType.NOT_FOUND_USER_ERROR));
        userRepository.delete(user);
        // 추후 논리적 삭제로 변경
        return ResponseEntity.ok(ApiResponse.success());
    }
}
