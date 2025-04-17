package kr.hhplus.be.ecommerce.domain.order;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private Integer historyId;

	@Column(name = "order_id")
	private Integer orderId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "total_price")
	private Integer totalPrice;

	@Column(name = "order_state")
	private Integer orderState;

	@Column(name = "cdate")
	private LocalDateTime cdate;

}
