package kr.hhplus.be.ecommerce.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_price")
	private BigDecimal productPrice;
	
	@Column(name = "stock")
	private Integer stock;
	
	@Column(name = "product_state")
	private Integer productState;
	
	@Column(name = "cdate")
	private LocalDateTime cdate;
	
	@Column(name = "udate")
	private LocalDateTime udate;
}
