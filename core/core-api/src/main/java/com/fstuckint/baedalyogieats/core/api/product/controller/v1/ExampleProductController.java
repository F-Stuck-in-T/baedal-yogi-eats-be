package com.fstuckint.baedalyogieats.core.api.product.controller.v1;

import com.fstuckint.baedalyogieats.core.api.product.controller.v1.request.ExampleRequestDto;
import com.fstuckint.baedalyogieats.core.api.product.controller.v1.response.ExampleResponseDto;
import com.fstuckint.baedalyogieats.core.api.product.domain.ExampleData;
import com.fstuckint.baedalyogieats.core.api.product.domain.ExampleResult;
import com.fstuckint.baedalyogieats.core.api.product.domain.ExampleProductService;
import com.fstuckint.baedalyogieats.core.api.product.support.response.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleProductController {

	private final ExampleProductService exampleExampleProductService;

	public ExampleProductController(ExampleProductService exampleExampleProductService) {
		this.exampleExampleProductService = exampleExampleProductService;
	}

	@GetMapping("/get/product/{exampleValue}")
	public ApiResponse<ExampleResponseDto> exampleGet(@PathVariable String exampleValue,
			@RequestParam String exampleParam) {
		ExampleResult result = exampleExampleProductService.processExample(new ExampleData(exampleValue, exampleParam));
		return ApiResponse.success(new ExampleResponseDto(result.data()));
	}

	@PostMapping("/post/product")
	public ApiResponse<ExampleResponseDto> examplePost(@RequestBody ExampleRequestDto request) {
		ExampleResult result = exampleExampleProductService.processExample(request.toExampleData());
		return ApiResponse.success(new ExampleResponseDto(result.data()));
	}

}
