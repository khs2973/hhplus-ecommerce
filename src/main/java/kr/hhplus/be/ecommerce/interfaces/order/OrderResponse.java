package kr.hhplus.be.ecommerce.interfaces.order;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class OrderResponse {

	private Integer orderId;
	private String userId;
	private Integer totalPrice;
	private BigDecimal remainingPoint;
	private BigDecimal discountPrice;
	private BigDecimal usedPoint;

	public OrderResponse(Integer orderId, String userId, Integer totalPrice, BigDecimal remainingPoint
						, BigDecimal discountPrice, BigDecimal usedPoint) {
		this.orderId = orderId;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.remainingPoint = remainingPoint;
		this.discountPrice = discountPrice;
		this.usedPoint = usedPoint;
	}
}