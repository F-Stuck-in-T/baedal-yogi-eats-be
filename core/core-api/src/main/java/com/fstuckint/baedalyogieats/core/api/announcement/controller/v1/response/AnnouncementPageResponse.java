package com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.announcement.domain.AnnouncementResult;

import java.util.List;

public record AnnouncementPageResponse(List<AnnouncementResult> announcementResultList, boolean hasNext) {

}
