package com.fstuckint.baedalyogieats.core.api.ai.domain;

import com.fstuckint.baedalyogieats.storage.db.core.ai.AiChatsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AiChats {

    private String question;
    private String answer;
    private UUID productUuid;

    public AiChatsEntity toEntity() {
        return new AiChatsEntity(question, answer, productUuid);
    }
}
