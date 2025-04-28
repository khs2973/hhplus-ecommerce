package kr.hhplus.be.ecommerce.interfaces.product;

import kr.hhplus.be.ecommerce.domain.product.ProductCommand.CommandProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
	
	private Integer productId;
	
	public CommandProduct toProductCommand() {
		return CommandProduct.of(productId);
	}
	
}
