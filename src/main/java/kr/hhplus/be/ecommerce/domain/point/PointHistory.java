package kr.hhplus.be.ecommerce.domain.point;

import java.math.BigDecimal;
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
@Table(name="point_history")
public class PointHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private Integer historyId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "before_point")
	private BigDecimal beforePoint;
	
	@Column(name = "after_point")
	private BigDecimal afterPoint;
	
	@Column(name = "point_type")
	private Integer pointType;
	
	@Column(name = "cdate")
	private LocalDateTime cdate;
	
}
