package kr.hhplus.be.ecommerce.interfaces.common;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

	NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "포인트가 부족합니다."),
	NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "유효하지 않은 사용자입니다."),
	CHARGE_POINT_MAX(HttpStatus.BAD_REQUEST, "최대 충전 금액 한도를 초과하셨습니다."),
	CHARGE_POINT_MIN(HttpStatus.BAD_REQUEST, "최소 충전 금액 이상을 충전하셔야합니다."),
	NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST, "해당 상품이 존재하지 않습니다.");

	private final HttpStatus errorCode;
	private final String message;
}
