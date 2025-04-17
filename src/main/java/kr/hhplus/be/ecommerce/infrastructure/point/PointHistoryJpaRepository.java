package kr.hhplus.be.ecommerce.infrastructure.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.ecommerce.domain.point.PointHistory;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistory, Long>{

}
