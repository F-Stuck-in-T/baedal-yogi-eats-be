package com.fstuckint.baedalyogieats.core.api.product.controller.v1.request;

import com.fstuckint.baedalyogieats.core.api.product.domain.ExampleData;

public record ExampleRequestDto(String data) {
	public ExampleData toExampleData() {
		return new ExampleData(data, data);
	}
}
