package kr.hhplus.be.ecommerce.domain.product;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductInfo {
	
	@Getter
	public static class InfoProduct {
		private final Integer productId;
		private final String productName;
		private final BigDecimal productPrice;
		private final Integer stock;
		
		private InfoProduct(Integer productId, String productName, BigDecimal productPrice, Integer stock) {
			this.productId = productId;
			this.productName = productName;
			this.productPrice = productPrice;
			this.stock = stock;
		}
		
		public static InfoProduct of(Integer productId, String productName, BigDecimal productPrice, Integer stock) {
			return new InfoProduct(productId, productName, productPrice, stock);
		}
	}
}
