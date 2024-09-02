package com.fstuckint.baedalyogieats.storage.db.core.ai;

import com.fstuckint.baedalyogieats.storage.db.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_ai_chats")
public class AiChatsEntity extends BaseEntity {

    private String question;

    private String answer;

}
