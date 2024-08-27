package com.fstuckint.baedalyogieats.storage.db.core.user;

import lombok.Getter;

@Getter
public enum UserRole {

    CUSTOMER(Authority.CUSTOMER), OWNER(Authority.OWNER), MANAGER(Authority.MANAGER), MASTER(Authority.MASTER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    private static class Authority {

        private static final String CUSTOMER = "ROLE_CUSTOMER";

        private static final String OWNER = "ROLE_OWNER";

        private static final String MANAGER = "ROLE_MANAGER";

        private static final String MASTER = "ROLE_MASTER";

    }

}
