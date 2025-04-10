package kr.hhplus.be.ecommerce.interfaces.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ApiResponse<T> {
	
	private String code;
	private String message;
	private T data;

	public static <T> ApiResponse<T> success(T data) {
		return ApiResponse.of("SUCCESS", "성공적으로 처리되었습니다.", data);
	}

	public static <T> ApiResponse<T> error(String code, String message) {
		return ApiResponse.of(code, message, null);
	}
	
}
