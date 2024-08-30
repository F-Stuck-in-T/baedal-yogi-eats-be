package com.fstuckint.baedalyogieats.core.api.announcement.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.announcement.domain.Announcement;

public record AnnouncementRequest(String title, String content) {
    public Announcement toAnnouncement() {
        return new Announcement(title, content);
    }
}
