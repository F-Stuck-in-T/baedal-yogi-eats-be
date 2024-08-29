package com.fstuckint.baedalyogieats.core.api.ai.domain;

import com.fstuckint.baedalyogieats.core.api.ai.controller.v1.response.ClientResponse;
import com.fstuckint.baedalyogieats.storage.db.core.ai.AiChatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AiChatsService {

    private final AiChatPilot aiChatPilot;

    public ClientResponse createProductDescription(String text) {
        return ClientResponse.of(aiChatPilot.createProductDescription(text));
    }

}
