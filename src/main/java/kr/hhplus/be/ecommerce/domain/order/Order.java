package kr.hhplus.be.ecommerce.domain.order;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer orderId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "total_price")
	private Integer totalPrice;
	
	@Column(name = "address")
	private String address ;
	
	@Column(name = "cdate")
	private LocalDateTime cdate;
	
	@Column(name = "order_state")
	private Integer orderState;
	
	
	
	
}