package kr.hhplus.be.ecommerce.infrastructure.point;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.ecommerce.domain.point.UserPoint;

public interface UserPointJpaRepository extends JpaRepository<UserPoint, String>{

	UserPoint findByUserId(String userId);
	
}
