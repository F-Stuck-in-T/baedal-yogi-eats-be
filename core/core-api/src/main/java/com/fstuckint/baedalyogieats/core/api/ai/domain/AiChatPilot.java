package com.fstuckint.baedalyogieats.core.api.ai.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstuckint.baedalyogieats.core.api.address.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.ai.controller.v1.request.ChatRequest;
import com.fstuckint.baedalyogieats.core.api.ai.controller.v1.response.ChatResponse;
import com.fstuckint.baedalyogieats.core.api.ai.support.error.AiChatsApiException;
import com.fstuckint.baedalyogieats.storage.db.core.ai.AiChatsEntity;
import com.fstuckint.baedalyogieats.storage.db.core.ai.AiChatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiChatPilot {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiUrl;

    private final RestTemplate restTemplate;

    private final AiChatsRepository aiChatsRepository;

    public String createProductDescription(String question) {
        String aiResponse = createAiResponse(question);
        aiChatsRepository.save(new AiChatsEntity(question, aiResponse));
        return aiResponse;

        /**
         * 뭐지? merge?
         *
         * Controller 에서 String 으로만 받아서 GEMINI 호출하면 Json 형태로 받아와서 ObjectMapper.readTree 로
         * contents 추출했는데
         *
         * record 로 바꿔서 Http Body 부분 받으니 GEMINI 가 String 만 딱 보냄;;
         *
         */
    }

    private String createAiResponse(String text) {
        String requestUrl = geminiUrl + "?key=" + geminiApiKey;
        ChatRequest chatRequest = new ChatRequest(text);
        ChatResponse response = restTemplate.postForObject(requestUrl, chatRequest, ChatResponse.class);
        return response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();
    }

}
