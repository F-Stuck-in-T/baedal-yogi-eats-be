package com.fstuckint.baedalyogieats.core.enums.order;

public enum OrderType {

    ONLINE("온라인 주문"), OFFLINE("오프라인 주문");

    private final String message;

    OrderType(String message) {
        this.message = message;
    }

}
