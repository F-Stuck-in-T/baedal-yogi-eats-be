package com.fstuckint.baedalyogieats.storage.db.core.ai;


import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AiChatsEntity extends BaseEntity {

    private String question;
    private String answer;

    private UUID productUuid;
}
