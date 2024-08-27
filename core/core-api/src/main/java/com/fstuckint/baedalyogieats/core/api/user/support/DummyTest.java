package com.fstuckint.baedalyogieats.core.api.user.support;

<<<<<<< HEAD
import com.fstuckint.baedalyogieats.storage.db.core.user.User;
=======

import com.fstuckint.baedalyogieats.storage.db.core.user.UserEntity;
>>>>>>> feature/tmp
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

        List<UserEntity> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new UserEntity("AAA" + i, "password", "AAA" + i, UserRole.CUSTOMER));
        }
        for (int i = 0; i < 30; i++) {
<<<<<<< HEAD
            list.add(new User("owner" + i, "password", "owner" + i, UserRole.OWNER));
=======
               list.add(new UserEntity("BBB" + i, "password", "BBB" + i, UserRole.OWNER));
>>>>>>> feature/tmp
        }
        for (int i = 0; i < 30; i++) {
            list.add(new UserEntity("CCC" + i, "password", "CCC" + i, UserRole.MANAGER));
        }

        userRepository.saveAll(list);
    }

}
