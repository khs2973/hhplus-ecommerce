package kr.hhplus.be.ecommerce.interfaces.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
	
	private final int code;
	private final String message;

	public static ErrorResponse fail(int code, String message) {
		return new ErrorResponse(code, message);
	}
}
