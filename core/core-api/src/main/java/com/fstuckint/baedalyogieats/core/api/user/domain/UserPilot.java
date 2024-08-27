package com.fstuckint.baedalyogieats.core.api.user.domain;

import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.UpdateUserDto;
import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.error.UserException;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserEntity;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPilot {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResult add(User user) {
        user.encodingPassword(passwordEncoder.encode(user.getPassword()));
        return UserResult.of(userRepository.save(user.toEntity()));
    }

    public UserResult findByUsernameAndIsDeletedFalse(String username) {
        return UserResult.of(userRepository.findByUsernameAndIsDeletedFalse(username).orElse(null));
    }

    public UserResult findByNicknameAndIsDeletedFalse(String nickname) {
        return UserResult.of(userRepository.findByNicknameAndIsDeletedFalse(nickname).orElse(null));
    }


    public Page<UserEntity> findAllUser(LocalDateTime cursor, PageRequest of) {
        return userRepository.findAllUserByIsDeletedFalse(
                cursor != null ? cursor : LocalDateTime.of(2000, 1, 1, 0, 0), of);
    }

    public UserEntity findByUuid(UUID id) {
        return userRepository.findByUuidAndIsDeletedFalse(id).orElseThrow(() -> new UserException(ErrorType.NOT_FOUND_ERROR));
    }

    public UserEntity updateUser(UUID id, UpdateUserDto updateUserDto) {
        UserEntity user = findByUuid(id);

        user.updateNickname(updateUserDto.getNickname());
        user.updatePassword(passwordEncoder.encode(updateUserDto.getPassword()));
        return userRepository.save(user);
    }

    public void findAndDelete(UUID id) {
        UserEntity user = findByUuid(id);
        userRepository.save(user.deleteUser());
    }
}
