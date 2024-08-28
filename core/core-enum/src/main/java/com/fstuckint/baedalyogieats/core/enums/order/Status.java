package com.fstuckint.baedalyogieats.core.enums.order;

public enum Status {

    PENDING("접수대기"), RECEIVED("접수완료"), SHIPPING("배송중"), DELIVERED("배송완료");

    Status(String description) {
        this.description = description;
    }

    private final String description;

}
