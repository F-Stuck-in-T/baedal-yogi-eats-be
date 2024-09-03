package com.fstuckint.baedalyogieats.core.api.product.support;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Sort;

public record Cursor(String cursor, long limit, String sortKey, Sort.Direction sort) {

    public UUID getUuid() {
        if (cursor == null || cursor.isEmpty()) {
            return null;
        }
        String[] parts = cursor.split("_");
        try {
            return UUID.fromString(parts[0]);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    public LocalDateTime getTimestamp() {
        if (cursor == null || cursor.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(cursor.split("_")[1]);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String encodeCursor(UUID uuid, LocalDateTime timestamp) {
        return uuid.toString() + "_" + timestamp.toString();
    }

    public static String createNextCursor(UUID uuid, LocalDateTime createdAt, LocalDateTime updatedAt, String sortKey) {
        LocalDateTime nextTimestamp = "createdAt".equals(sortKey) ? createdAt : updatedAt;
        return encodeCursor(uuid, nextTimestamp);
    }

}
