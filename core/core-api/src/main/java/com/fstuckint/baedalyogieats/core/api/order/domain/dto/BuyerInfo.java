package com.fstuckint.baedalyogieats.core.api.order.domain.dto;

import com.fstuckint.baedalyogieats.storage.db.core.order.*;
import lombok.*;

import java.util.*;

@Getter
@Builder
public class BuyerInfo {
    private String nickname;
    private UUID userUuid;

    public static BuyerInfo of(BuyerEntity buyerEntity) {
        return BuyerInfo.builder()
                .nickname(buyerEntity.getNickname())
                .userUuid(buyerEntity.getUserUuid())
                .build();
    }
}
