package com.fstuckint.baedalyogieats.core.api.announcement.domain;

import com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.request.AnnouncementRequest;
import com.fstuckint.baedalyogieats.core.api.announcement.support.error.AnnouncementException;
import com.fstuckint.baedalyogieats.core.api.announcement.support.error.ErrorType;
import com.fstuckint.baedalyogieats.storage.db.core.announcement.AnnouncementEntity;
import com.fstuckint.baedalyogieats.storage.db.core.announcement.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnnouncementPilot {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementResult createAnnouncement(AnnouncementRequest announcementRequest) {
        return AnnouncementResult.of(announcementRepository.save(announcementRequest.toAnnouncement().toEntity()));
    }

    public AnnouncementResult getAnnouncementByUuid(UUID announcementUuid) {
        return AnnouncementResult.of(announcementRepository.findByUuidAndIsDeletedFalse(announcementUuid)
            .orElseThrow(() -> new AnnouncementException(ErrorType.NOT_FOUND_ERROR)));
    }

    public Page<AnnouncementEntity> getAnnouncementList(LocalDateTime cursor, Integer limit, String sortKey,
            String direction) {
        if (cursor == null)
            cursor = LocalDateTime.of(2000, 1, 1, 0, 0);
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.fromString(direction), sortKey));
        return announcementRepository.findAll(cursor, pageRequest);
    }

    public AnnouncementResult updateAnnouncement(UUID announcementUuid, AnnouncementRequest announcementRequest) {
        AnnouncementEntity announcementEntity = announcementRepository.findByUuidAndIsDeletedFalse(announcementUuid)
            .orElseThrow(() -> new AnnouncementException(ErrorType.NOT_FOUND_ERROR));
        Announcement announcement = announcementRequest.toAnnouncement();
        return AnnouncementResult.of(announcementRepository
            .save(announcementEntity.update(announcement.getTitle(), announcement.getContent())));
    }

    public AnnouncementResult deleteAnnouncement(UUID announcementUuid) {
        AnnouncementEntity announcementEntity = announcementRepository.findByUuidAndIsDeletedFalse(announcementUuid)
            .orElseThrow(() -> new AnnouncementException(ErrorType.NOT_FOUND_ERROR));
        return AnnouncementResult.of(announcementRepository.save(announcementEntity.delete()));

    }

}
