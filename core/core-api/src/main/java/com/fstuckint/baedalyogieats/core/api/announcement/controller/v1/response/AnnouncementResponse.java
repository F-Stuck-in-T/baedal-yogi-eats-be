package com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.announcement.domain.AnnouncementResult;

import java.util.UUID;

public record AnnouncementResponse(UUID uuid, String title, String content) {

    public static AnnouncementResponse of(AnnouncementResult announcementResult) {
        return new AnnouncementResponse(announcementResult.uuid(), announcementResult.title(),
                announcementResult.content());
    }
}
