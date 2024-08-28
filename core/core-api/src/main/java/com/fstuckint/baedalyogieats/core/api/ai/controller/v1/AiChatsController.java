package com.fstuckint.baedalyogieats.core.api.ai.controller.v1;

import com.fstuckint.baedalyogieats.core.api.ai.controller.v1.request.ProductDescriptionRequestDto;
import com.fstuckint.baedalyogieats.core.api.ai.domain.AiChatsService;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiChatsController {

    private final AiChatsService aiChatsService;

    @PostMapping("/product/description")
    public ResponseEntity<ApiResponse<?>> createProductDescription(@RequestBody ProductDescriptionRequestDto requestDto,
            HttpServletRequest request) {
        return aiChatsService.createProductDescription(requestDto, request);
    }

}
