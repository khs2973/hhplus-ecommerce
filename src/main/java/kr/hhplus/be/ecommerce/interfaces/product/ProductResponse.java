package kr.hhplus.be.ecommerce.interfaces.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import kr.hhplus.be.ecommerce.domain.product.ProductInfo.InfoProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
	
	private Integer productId;
	private String productName;
	private BigDecimal productPrice;
	private Integer stock;
	
	public static ProductResponse from (InfoProduct infoProduct) {
		return new ProductResponse(infoProduct.getProductId()
								 , infoProduct.getProductName()
								 , infoProduct.getProductPrice()
								 , infoProduct.getStock());
	}
	
}
