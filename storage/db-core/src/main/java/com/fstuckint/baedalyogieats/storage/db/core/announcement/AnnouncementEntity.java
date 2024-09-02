package com.fstuckint.baedalyogieats.storage.db.core.announcement;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "p_announcement")
public class AnnouncementEntity extends BaseEntity {

    private String title;

    private String content;

    private boolean isDeleted = false;

    public AnnouncementEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public AnnouncementEntity delete() {
        isDeleted = true;
        return this;
    }

    public AnnouncementEntity update(String title, String content) {
        this.title = title == null ? this.title : title;
        this.content = content == null ? this.content : content;
        return this;
    }

}
