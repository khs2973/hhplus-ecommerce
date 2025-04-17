package kr.hhplus.be.ecommerce.domain.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	private String userId;
	private String address;
	private Integer type;
	private LocalDateTime cdate;
}
