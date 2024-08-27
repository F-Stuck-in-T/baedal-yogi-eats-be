package com.fstuckint.baedalyogieats.client.example;

import com.fstuckint.baedalyogieats.client.example.model.ExampleClientResult;

record ExampleResponseDto(String exampleResponseValue) {
    ExampleClientResult toResult() {
        return new ExampleClientResult(exampleResponseValue);
    }
}
