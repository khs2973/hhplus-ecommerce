package kr.hhplus.be.ecommerce.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCommand {

	@Getter
	public static class Create {
		private final Integer productId;
		
		public Create(Integer productId) {
			this.productId = productId;
		}
		
		public static Create of(Integer productId) {
			return new Create(productId);
		}
	}
	
}
