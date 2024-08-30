package com.fstuckint.baedalyogieats.core.api.announcement.domain;

import com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.response.AnnouncementPageResponse;
import com.fstuckint.baedalyogieats.storage.db.core.announcement.AnnouncementEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public record AnnouncementResult(UUID uuid, String title, String content) {

    public static AnnouncementResult of(AnnouncementEntity announcementEntity) {
        return new AnnouncementResult(announcementEntity.getUuid(), announcementEntity.getTitle(),
                announcementEntity.getContent());

    }

    public static AnnouncementPageResponse toAnnouncementPageResponse(Page<AnnouncementEntity> announcementEntityPage) {
        List<AnnouncementResult> list = announcementEntityPage.stream().map(AnnouncementResult::of).toList();
        return new AnnouncementPageResponse(list, announcementEntityPage.hasNext());
    }
}
