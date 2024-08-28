package com.fstuckint.baedalyogieats.core.api.order.domain.dto;

import com.fstuckint.baedalyogieats.core.enums.order.*;
import lombok.*;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor
public class RegisterOrderCommand {

    private Type type;

    private RegisterBuyerCommand buyer;

    private List<RegisterOrderItemCommand> products = new ArrayList<>();

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RegisterBuyerCommand {

        private String nickname;

        private UUID userUuid;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RegisterOrderItemCommand {

        private String name;

        private Integer unitPrice;

        private UUID productUuid;

    }

}
