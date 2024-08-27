package com.fstuckint.baedalyogieats.core.api.order.domain;

import org.springframework.stereotype.Service;

@Service
public class ExampleOrderService {

    public ExampleResult processExample(ExampleData exampleData) {
        return new ExampleResult(exampleData.value());
    }

}
