package com.fstuckint.baedalyogieats.core.api.announcement.domain;

import com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.request.AnnouncementRequest;
import com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.response.AnnouncementPageResponse;
import com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.response.AnnouncementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementPilot announcementPilot;

    @Transactional
    public AnnouncementResponse createAnnouncement(AnnouncementRequest announcementRequest) {
        return AnnouncementResponse.of(announcementPilot.createAnnouncement(announcementRequest));
    }

    @Transactional(readOnly = true)
    public AnnouncementResponse getAnnouncementByUuid(UUID announcementUuid) {
        return AnnouncementResponse.of(announcementPilot.getAnnouncementByUuid(announcementUuid));
    }

    @Transactional(readOnly = true)
    public AnnouncementPageResponse getAnnouncementList(LocalDateTime cursor, Integer limit, String sortKey,
            String direction) {
        return AnnouncementResult
            .toAnnouncementPageResponse(announcementPilot.getAnnouncementList(cursor, limit, sortKey, direction));
    }

    @Transactional
    public AnnouncementResponse updateAnnouncement(UUID announcementUuid, AnnouncementRequest announcementRequest) {
        return AnnouncementResponse.of(announcementPilot.updateAnnouncement(announcementUuid, announcementRequest));
    }

    @Transactional
    public AnnouncementResponse deleteAnnouncement(UUID announcementUuid) {
        return AnnouncementResponse.of(announcementPilot.deleteAnnouncement(announcementUuid));
    }

}
