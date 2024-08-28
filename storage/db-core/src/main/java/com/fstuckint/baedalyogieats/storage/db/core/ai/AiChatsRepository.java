package com.fstuckint.baedalyogieats.storage.db.core.ai;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiChatsRepository extends JpaRepository<AiChatsEntity, UUID> {

}
