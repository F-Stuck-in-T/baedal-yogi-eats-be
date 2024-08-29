package com.fstuckint.baedalyogieats.core.api.store.support;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Sort;

public record Cursor(String cursor, long limit, String sortKey, Sort.Direction sort) {

    public UUID getUuid() {
        return cursor != null ? UUID.fromString(cursor.split("_")[0]) : null;
    }

    public LocalDateTime getTimestamp() {
        return cursor != null ? LocalDateTime.parse(cursor.split("_")[1]) : null;
    }

    public static String encodeCursor(UUID uuid, LocalDateTime timestamp) {
        return uuid.toString() + "_" + timestamp.toString();
    }

    public static String createNextCursor(UUID uuid, LocalDateTime createdAt, LocalDateTime updatedAt, String sortKey) {
        LocalDateTime nextTimestamp = "createdAt".equals(sortKey) ? createdAt : updatedAt;
        return encodeCursor(uuid, nextTimestamp);
    }

}
