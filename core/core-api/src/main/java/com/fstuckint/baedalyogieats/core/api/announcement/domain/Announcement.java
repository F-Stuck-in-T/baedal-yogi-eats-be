package com.fstuckint.baedalyogieats.core.api.announcement.domain;

import com.fstuckint.baedalyogieats.storage.db.core.announcement.AnnouncementEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    private String title;

    private String content;

    public AnnouncementEntity toEntity() {
        return new AnnouncementEntity(title, content);
    }

}
