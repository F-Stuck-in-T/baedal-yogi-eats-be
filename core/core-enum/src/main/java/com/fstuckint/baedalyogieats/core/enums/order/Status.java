package com.fstuckint.baedalyogieats.core.enums.order;

public enum Status {

    RECEIVED("접수 완료"), SHIPPING("배송중"), DELIVERED("배송 완료");

    Status(String message) {
        this.message = message;
    }

    private final String message;

}
