package kr.hhplus.be.ecommerce.domain.order;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_product")
public class OrderProduct {

	@Column(name = "op_id")
	private Integer opId;

	@Column(name = "order_id")
	private Integer orderId;
	
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "order_quantity")
	private Integer orderQuantity;
	
	@Column(name = "discount_price")
	private Integer discountPrice;
	
	@Column(name = "product_price")
	private BigDecimal productPrice;
	
}
