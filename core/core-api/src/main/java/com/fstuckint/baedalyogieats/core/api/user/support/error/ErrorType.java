package com.fstuckint.baedalyogieats.core.api.user.support.error;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

	DEFAULTS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "An unexpected error has occurred.", LogLevel.ERROR),
	USERNAME_PASSWORD_BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Confirm username and password.", LogLevel.ERROR),
	DUPLICATE_NICKNAME_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Duplicate nickname.", LogLevel.ERROR),
	NOT_MATCH_ROLE_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Not match user role.", LogLevel.ERROR),
	NOT_ACCEPTABLE_ROLE_ERROR(HttpStatus.NOT_ACCEPTABLE, ErrorCode.E400, "Not Acceptable user role", LogLevel.ERROR),
	NOT_FOUND_USER_ERROR(HttpStatus.NOT_FOUND, ErrorCode.E400, "Not found user", LogLevel.ERROR),
	TOKEN_ERROR(HttpStatus.NOT_ACCEPTABLE, ErrorCode.E400, "Not valid or empty token", LogLevel.ERROR),
	NOT_VALID_USERNAME_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "Not valid username and password.", LogLevel.ERROR);




	private final HttpStatus status;

	private final ErrorCode code;

	private final String message;

	private final LogLevel logLevel;

	ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {

		this.status = status;
		this.code = code;
		this.message = message;
		this.logLevel = logLevel;
	}

}
