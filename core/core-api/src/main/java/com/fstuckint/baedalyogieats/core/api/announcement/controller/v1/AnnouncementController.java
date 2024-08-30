package com.fstuckint.baedalyogieats.core.api.announcement.controller.v1;

import com.fstuckint.baedalyogieats.core.api.ai.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.request.AnnouncementRequest;
import com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.response.AnnouncementPageResponse;
import com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.response.AnnouncementResponse;
import com.fstuckint.baedalyogieats.core.api.announcement.domain.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    // 어드민만 가능
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createAnnouncemnet(@RequestBody AnnouncementRequest announcementRequest) {
        AnnouncementResponse data = announcementService.createAnnouncement(announcementRequest);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 모든 사람 가능
    @GetMapping("/{announcementUuid}")
    public ResponseEntity<ApiResponse<?>> getAnnouncementByUuid(@PathVariable UUID announcementUuid) {
        AnnouncementResponse data = announcementService.getAnnouncementByUuid(announcementUuid);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 모든 사람 가능
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAnnouncementList(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "createdAt") String sortKey,
            @RequestParam(defaultValue = "ASC") String direction) {
        AnnouncementPageResponse data = announcementService.getAnnouncementList(cursor, limit, sortKey, direction);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 관리자만
    @PutMapping("/{announcementUuid}")
    public ResponseEntity<ApiResponse<?>> updateAnnouncement(@PathVariable UUID announcementUuid,
            @RequestBody AnnouncementRequest announcementRequest) {
        AnnouncementResponse data = announcementService.updateAnnouncement(announcementUuid, announcementRequest);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 관리자만 가능
    @DeleteMapping("/{announcementUuid}")
    public ResponseEntity<ApiResponse<?>> deleteAnnouncement(@PathVariable UUID announcementUuid) {
        AnnouncementResponse data = announcementService.deleteAnnouncement(announcementUuid);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
