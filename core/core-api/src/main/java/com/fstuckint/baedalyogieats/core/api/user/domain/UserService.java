package com.fstuckint.baedalyogieats.core.api.user.domain;

import com.fstuckint.baedalyogieats.core.api.common.jwt.UserChecker;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.LoginDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.SignupDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.UpdateUserDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.response.UserPageResponse;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.error.UserException;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

<<<<<<< HEAD
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @Transactional
    public ResponseEntity<ApiResponse<?>> signUp(SignupDto signupDto) {
        User usernameCheck = userRepository.findByUsername(signupDto.getUsername()).orElse(null);
        if (usernameCheck != null)
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);
        User nicknameCheck = userRepository.findByNickname(signupDto.getNickname()).orElse(null);
        if (nicknameCheck != null)
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);

        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        userRepository
            .save(new User(signupDto.getUsername(), encodedPassword, signupDto.getNickname(), signupDto.getUserRole()));
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> createToken(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
            .orElseThrow(() -> new UserException(ErrorType.BAD_REQUEST_ERROR));
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);

        String token = jwtUtils.createToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok().header(JwtUtils.AUTHORIZATION_HEADER, token).body(ApiResponse.success(token));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> getUserList(LocalDateTime cursor, Integer limit, String sortKey,
            String direction, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (!jwtUtils.validationToken(token))
            throw new UserException(ErrorType.TOKEN_ERROR);

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortKey);
        PageRequest sortedPage = PageRequest.of(0, limit, sort);

        Page<User> pageList = userRepository
            .findAllByCursor(cursor != null ? cursor : LocalDateTime.of(2000, 1, 1, 0, 0), sortedPage);
        List<UserInfoDto> dtoList = pageList.stream().map(UserInfoDto::new).toList();

        return ResponseEntity.ok(ApiResponse.success(dtoList));
        // TODO: ApiResponse data 에 ... list 하고 hasNext 어떻게 같이 넣을까.. ( ObjectMapper
        // 써야하나..? )
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<?>> getUser(UUID id, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (token == null || !jwtUtils.validationToken(token))
            throw new UserException(ErrorType.TOKEN_ERROR);
        User user = userRepository.findByUuidAndIsDeletedFalse(id)
            .orElseThrow(() -> new UserException(ErrorType.BAD_REQUEST_ERROR));
        return ResponseEntity.ok(ApiResponse.success(new UserInfoDto(user)));
=======
    private final UserPilot userPilot;
    private final UserChecker userChecker;
    private final JwtUtils jwtUtils;

    @Transactional
    public void signUp(SignupDto signupDto) {
        User user = signupDto.toUser();
        if (userPilot.findByUsernameAndIsDeletedFalse(user.getUsername()) != null) throw new UserException(ErrorType.BAD_REQUEST_ERROR);
        if (userPilot.findByNicknameAndIsDeletedFalse(user.getNickname()) != null) throw new UserException(ErrorType.BAD_REQUEST_ERROR);
        userPilot.add(user);
    }

    @Transactional(readOnly = true)
    public String createToken(LoginDto loginDto) {
        UserResult user = userPilot.findByUsernameAndIsDeletedFalse(loginDto.username());
        if (user == null) throw new UserException(ErrorType.BAD_REQUEST_ERROR);
        if (!userChecker.checkPassword(loginDto.password(), user.password())) throw new UserException(ErrorType.BAD_REQUEST_ERROR);
        return jwtUtils.createToken(user.uuid(), user.username(), user.userRole());
>>>>>>> feature/tmp
    }

    @Transactional
    public void deleteToken(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
<<<<<<< HEAD
        if (token == null || !jwtUtils.validationToken(token))
            throw new UserException(ErrorType.TOKEN_ERROR);
        User user = userRepository.findByUuidAndIsDeletedFalse(id)
            .orElseThrow(() -> new UserException(ErrorType.NOT_FOUND_ERROR));
        User checkNickname = userRepository.findByNickname(updateUserDto.getNickname()).orElse(null);
        if (checkNickname != null)
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);

        user.updateNickname(updateUserDto.getNickname());
        user.updatePassword(passwordEncoder.encode(updateUserDto.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.success(new UserInfoDto(user)));
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(ErrorType.NOT_FOUND_ERROR));
        userRepository.save(user.deleteUser());
        return ResponseEntity.ok(ApiResponse.success());
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> deleteToken(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (token == null || !jwtUtils.validationToken(token))
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);
=======
        userChecker.checkTokenValid(token);
>>>>>>> feature/tmp
        jwtUtils.addBlacklist(token);
    }

<<<<<<< HEAD
=======
    @Transactional(readOnly = true)
    public UserPageResponse getUserList(LocalDateTime cursor, Integer limit, String sortKey, String direction, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        Page<UserEntity> userPage = userPilot.findAllUser(cursor, PageRequest.of(0, limit, Sort.by(Sort.Direction.fromString(direction), sortKey)));
        return UserResult.toUserPageResponse(userPage);
    }

    @Transactional(readOnly = true)
    public UserResult getUser(UUID id, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);

        UserEntity user = userPilot.findByUuid(id);
        return UserResult.of(user);
    }

    @Transactional
    public UserResult updateUser(UUID id, UpdateUserDto updateUserDto, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        if (!userChecker.checkAdmin(token)){ userChecker.checkIdentityByUserUuid(token, id);}
        return UserResult.of(userPilot.updateUser(id, updateUserDto));
    }

    @Transactional
    public void deleteUser(UUID id, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        if(!userChecker.checkAdmin(token)){ userChecker.checkIdentityByUserUuid(token, id);}
        userPilot.findAndDelete(id);
    }


>>>>>>> feature/tmp
}
