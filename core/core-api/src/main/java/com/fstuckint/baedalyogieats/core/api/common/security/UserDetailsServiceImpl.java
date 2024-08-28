package com.fstuckint.baedalyogieats.core.api.common.security;

import com.fstuckint.baedalyogieats.core.api.user.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.user.support.error.UserException;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserEntity;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: {}", username);
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserException(ErrorType.NOT_FOUND_ERROR));
        return new UserDetailsImpl(userEntity);
    }

}
