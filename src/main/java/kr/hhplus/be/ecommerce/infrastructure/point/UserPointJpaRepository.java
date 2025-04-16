package kr.hhplus.be.ecommerce.infrastructure.point;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.point.UserPoint;

@Repository
public interface UserPointJpaRepository extends JpaRepository<UserPoint, String>{

	UserPoint findByUserId(String userId);
	
}
