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
    }

    @Transactional
    public void deleteToken(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        userChecker.checkTokenValid(token);
        jwtUtils.addBlacklist(token);
    }

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


}
