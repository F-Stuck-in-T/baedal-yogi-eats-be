package com.fstuckint.baedalyogieats.core.api.ai.domain;

import com.fstuckint.baedalyogieats.core.api.address.support.error.ErrorType;
import com.fstuckint.baedalyogieats.core.api.ai.controller.v1.request.ProductDescriptionRequestDto;
import com.fstuckint.baedalyogieats.core.api.ai.support.error.AiChatsApiException;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import com.fstuckint.baedalyogieats.storage.db.core.ai.AiChatsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiChatsService {

    private final AiChatsRepository aiChatsRepository;
    private final JwtUtils jwtUtils;

    public ResponseEntity<ApiResponse<?>> createProductDescription(ProductDescriptionRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (!jwtUtils.checkAdmin(token) || !jwtUtils.checkOwner(token)) throw new AiChatsApiException(ErrorType.ROLE_ERROR);

        return null;
    }
}
