package kr.hhplus.be.ecommerce.domain.product;

import java.math.BigDecimal;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductInfo {
	
	@Getter
	public static class Create {
		private final Integer productId;
		private final String productName;
		private final BigDecimal productPrice;
		private final Integer stock;
		
		private Create(Integer productId, String productName, BigDecimal productPrice, Integer stock) {
			this.productId = productId;
			this.productName = productName;
			this.productPrice = productPrice;
			this.stock = stock;
		}
		
		public static Create of(Integer productId, String productName, BigDecimal productPrice, Integer stock) {
			return new Create(productId, productName, productPrice, stock);
		}
	}
	
	@Getter
	public static class TopRankProductInfo {
		private final List<ProductOrderRank> products;
		
		private TopRankProductInfo(List<ProductOrderRank> products) {
			this.products = products;
		}
		
		public static TopRankProductInfo of(List<ProductOrderRank> products) {
			return new TopRankProductInfo(products);
		}
		
	}
}
