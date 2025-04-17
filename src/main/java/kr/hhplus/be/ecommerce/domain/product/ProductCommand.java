package kr.hhplus.be.ecommerce.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCommand {

	@Getter
	public static class CommandProduct {
		private final Integer productId;
		
		public CommandProduct(Integer productId) {
			this.productId = productId;
		}
		
		public static CommandProduct of(Integer productId) {
			return new CommandProduct(productId);
		}
	}
	
}
