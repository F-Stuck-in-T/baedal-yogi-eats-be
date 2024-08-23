package com.fstuckint.baedalyogieats.core.api.product.domain;

import org.springframework.stereotype.Service;

@Service
public class ExampleProductService {

	public ExampleResult processExample(ExampleData exampleData) {
		return new ExampleResult(exampleData.value());
	}

}
