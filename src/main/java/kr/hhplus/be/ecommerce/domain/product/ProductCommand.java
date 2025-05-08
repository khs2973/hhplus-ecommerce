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
	
	@Getter
	public static class TopRank {
		
		private final int dateType;
		
		public int toDays() {
			return switch (dateType) {
			case 1 -> 3;
			case 2 -> 7;
			case 3 -> 30;
			default -> throw new IllegalArgumentException("Invalid dateType: " + dateType);
			};
		}
		
		public TopRank(int dateType) {
			this.dateType = dateType;
		}
		
		public static TopRank of(int dateType) {
			return new TopRank(dateType);
		}
	}
	
}
