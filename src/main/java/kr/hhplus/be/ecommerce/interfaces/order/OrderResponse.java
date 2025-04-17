package kr.hhplus.be.ecommerce.interfaces.order;

import java.math.BigDecimal;

import kr.hhplus.be.ecommerce.domain.order.OrderInfo.InfoOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

	private Integer orderId;
	private String userId;
	private BigDecimal remainingPoint;
	private BigDecimal discountPrice;
	private BigDecimal usePoint;

	public static OrderResponse from (InfoOrder infoOrder) {
		return new OrderResponse(infoOrder.getOrderId()
							   , infoOrder.getUserId()
							   , infoOrder.getRemainingPoint()
							   , infoOrder.getDiscount()
							   , infoOrder.getUsePoint());
	}
}