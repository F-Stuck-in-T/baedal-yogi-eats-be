package com.fstuckint.baedalyogieats.core.enums.order;

public enum OrderStatus {

    PENDING("접수대기"), RECEIVED("접수완료"), SHIPPING("배송중"), DELIVERED("배송완료");

    OrderStatus(String description) {
        this.description = description;
    }

    private final String description;

}
