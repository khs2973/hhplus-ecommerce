package kr.hhplus.be.ecommerce.interfaces.common;

public class CustomException extends RuntimeException {

	public CustomException(ErrorEnum errorEnum) {
		super(errorEnum.getMessage());
	}
}
