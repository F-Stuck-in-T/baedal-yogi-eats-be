package com.fstuckint.baedalyogieats.core.api.order.controller.v1;

import com.fstuckint.baedalyogieats.core.api.order.controller.v1.request.ExampleRequestDto;
import com.fstuckint.baedalyogieats.core.api.order.controller.v1.response.ExampleResponseDto;
import com.fstuckint.baedalyogieats.core.api.order.domain.ExampleData;
import com.fstuckint.baedalyogieats.core.api.order.domain.ExampleResult;
import com.fstuckint.baedalyogieats.core.api.order.domain.ExampleOrderService;
import com.fstuckint.baedalyogieats.core.api.order.support.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleOrderController {

	private final ExampleOrderService exampleExampleOrderService;

	public ExampleOrderController(ExampleOrderService exampleExampleOrderService) {
		this.exampleExampleOrderService = exampleExampleOrderService;
	}

	@GetMapping("/get/order/{exampleValue}")
	public ApiResponse<ExampleResponseDto> exampleGet(@PathVariable String exampleValue,
			@RequestParam String exampleParam) {
		ExampleResult result = exampleExampleOrderService.processExample(new ExampleData(exampleValue, exampleParam));
		return ApiResponse.success(new ExampleResponseDto(result.data()));
	}

	@PostMapping("/post/order")
	public ApiResponse<ExampleResponseDto> examplePost(@RequestBody ExampleRequestDto request) {
		ExampleResult result = exampleExampleOrderService.processExample(request.toExampleData());
		return ApiResponse.success(new ExampleResponseDto(result.data()));
	}

}
