package kr.hhplus.be.ecommerce.infrastructure.user;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.ecommerce.domain.user.User;

public interface UserJpaRepository extends JpaRepository<User, String>{

}
