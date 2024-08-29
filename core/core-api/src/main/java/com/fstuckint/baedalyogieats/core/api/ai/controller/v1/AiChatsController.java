package com.fstuckint.baedalyogieats.core.api.ai.controller.v1;

import com.fstuckint.baedalyogieats.core.api.ai.controller.v1.request.ClientRequest;
import com.fstuckint.baedalyogieats.core.api.ai.controller.v1.response.ClientResponse;
import com.fstuckint.baedalyogieats.core.api.ai.domain.AiChatsService;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiChatsController {

    private final AiChatsService aiChatsService;

    @PostMapping("/product/description")
    public ResponseEntity<ApiResponse<?>> createProductDescription(@RequestBody ClientRequest clientRequest) {
        ClientResponse data = aiChatsService.createProductDescription(clientRequest.text());
        return ResponseEntity.ok(ApiResponse.success(data));
    }

}