package com.fstuckint.baedalyogieats.core.api.user.domain;

import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import java.util.UUID;

public record CurrentUser(UUID uuid, String username, UserRole role) {

    public boolean isAdmin() {
        return role.equals(UserRole.MASTER) || role.equals(UserRole.MANAGER);
    }

    public boolean isOwner() {
        return role.equals(UserRole.OWNER);
    }

    public boolean isCustomer() {
        return role.equals(UserRole.CUSTOMER);
    }
}
