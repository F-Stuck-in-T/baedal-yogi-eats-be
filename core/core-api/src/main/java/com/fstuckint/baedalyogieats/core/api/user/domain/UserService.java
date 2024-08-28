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

    private final JwtUtils jwtUtils;

    @Transactional
    public void signUp(SignupDto signupDto) {
        User user = signupDto.toUser();
        if (userPilot.findByUsernameAndIsDeletedFalse(user.getUsername()) != null)
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);
        if (userPilot.findByNicknameAndIsDeletedFalse(user.getNickname()) != null)
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);
        userPilot.add(user);
    }


    @Transactional
    public void deleteToken(String bearerToken) {
        jwtUtils.addBlacklist(bearerToken);
    }

    @Transactional(readOnly = true)
    public UserPageResponse getUserList(LocalDateTime cursor, Integer limit, String sortKey, String direction) {
        Page<UserEntity> userPage = userPilot.findAllUser(cursor,
                PageRequest.of(0, limit, Sort.by(Sort.Direction.fromString(direction), sortKey)));
        return UserResult.toUserPageResponse(userPage);
    }

    @Transactional(readOnly = true)
    public UserResult getUser(UUID id) {
        UserEntity user = userPilot.findByUuid(id);
        return UserResult.of(user);
    }

    @Transactional
    public UserResult updateUser(UUID id, UpdateUserDto updateUserDto, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            return UserResult.of(userPilot.updateUser(id, updateUserDto));
        else if (jwtUtils.checkCustomer(bearerToken) && jwtUtils.checkIdentityToken(id, bearerToken))
            return UserResult.of(userPilot.updateUser(id, updateUserDto));
        else
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);
    }

    @Transactional
    public void deleteUser(UUID id, String bearerToken) {
        if (jwtUtils.checkAdmin(bearerToken))
            userPilot.findAndDelete(id);
        else if (jwtUtils.checkCustomer(bearerToken) && jwtUtils.checkIdentityToken(id, bearerToken))
            userPilot.findAndDelete(id);
        else
            throw new UserException(ErrorType.BAD_REQUEST_ERROR);
    }

    /**
     * 당신은 필터로 대체되었습니다.
     *
     * @Transactional(readOnly = true) public String createToken(LoginDto loginDto) {
     * log.info("너 어차피 동작 안하잖아!"); UserResult user =
     * userPilot.findByUsernameAndIsDeletedFalse(loginDto.username()); return
     * jwtUtils.createToken(user.uuid(), user.username(), user.userRole()); }
     **/

}
