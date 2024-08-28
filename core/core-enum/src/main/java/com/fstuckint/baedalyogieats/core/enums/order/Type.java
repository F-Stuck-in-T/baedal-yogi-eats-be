package com.fstuckint.baedalyogieats.core.enums.order;

public enum Type {

    ONLINE("온라인 주문"), OFFLINE("오프라인 주문");

    private final String message;

    Type(String message) {
        this.message = message;
    }

}
