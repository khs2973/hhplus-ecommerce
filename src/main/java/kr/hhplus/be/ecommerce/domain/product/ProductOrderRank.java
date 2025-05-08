package kr.hhplus.be.ecommerce.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductOrderRank {
	private Integer productId;
	private String productName;
	private Integer totalQuantity;
}
