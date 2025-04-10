package kr.hhplus.be.ecommerce.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderState {
	ORDERED(1, "주문 완료")
	, CANCELED(2, "주문 취소")
	, COMPLETED(3, "주문 완료");

	private final int code;
	private final String description;
}
