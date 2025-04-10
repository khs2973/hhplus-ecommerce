package kr.hhplus.be.ecommerce.infrastructure.user;

import java.util.Optional;

import kr.hhplus.be.ecommerce.domain.user.User;

public interface UserRepository {

	Optional<User> findByUserId(String userId);
}
