package kr.hhplus.be.ecommerce.infrastructure.point;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;

public interface UserPointJpaRepository extends JpaRepository<UserPoint, String>{

	UserPoint findByUserId(String userId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select up from UserPoint up where up.userId = :userId")
	Optional<UserPoint> findByUserIdForUpdate(@Param("userId") String userId);
	
}
