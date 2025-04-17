package kr.hhplus.be.ecommerce.domain.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DynamicUpdate
@NoArgsConstructor
@Table(name="user")
public class User {
	
	@Id	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "cdate")
	private LocalDateTime cdate;
	
	public User(String userId, String address, LocalDateTime cdate) {
		this.userId = userId;
		this.address = address;
		this.cdate = cdate;
	}
	
}
