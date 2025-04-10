package kr.hhplus.be.ecommerce.interfaces.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
	Integer productId;
	String productName;
	BigDecimal productPrice;
	Integer stock;
	Integer productState;
	LocalDateTime cdate;
	LocalDateTime udate;
}
