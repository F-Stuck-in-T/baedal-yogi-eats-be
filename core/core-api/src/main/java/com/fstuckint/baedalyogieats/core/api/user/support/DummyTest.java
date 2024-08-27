package com.fstuckint.baedalyogieats.core.api.user.support;

import com.fstuckint.baedalyogieats.storage.db.core.user.User;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRepository;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DummyTest {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {

        List<User> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new User("consumer" + i, "password", "consumer" + i, UserRole.CUSTOMER));
        }
        for (int i = 0; i < 30; i++) {
            list.add(new User("owner" + i, "password", "owner" + i, UserRole.OWNER));
        }
        for (int i = 0; i < 30; i++) {
            list.add(new User("manager" + i, "password", "manager" + i, UserRole.MANAGER));
        }

        userRepository.saveAll(list);
    }

}
