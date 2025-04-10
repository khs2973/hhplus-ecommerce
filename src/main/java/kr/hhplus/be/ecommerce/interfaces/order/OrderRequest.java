package kr.hhplus.be.ecommerce.interfaces.order;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	private Integer opId;
	private Integer orderId;
	private Integer productId;
	private String userId;
	private Integer orderQuantity;
	private BigDecimal discountPrice;
	private BigDecimal productPrice;
	private BigDecimal usedPoint;
}