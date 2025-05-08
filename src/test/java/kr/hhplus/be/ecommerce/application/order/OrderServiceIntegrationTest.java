package kr.hhplus.be.ecommerce.application.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import kr.hhplus.be.ecommerce.application.order.OrderCriteria.CriteriaOrderProduct;
import kr.hhplus.be.ecommerce.application.product.ProductService;
import kr.hhplus.be.ecommerce.domain.coupon.Coupon;
import kr.hhplus.be.ecommerce.domain.coupon.CouponEnum;
import kr.hhplus.be.ecommerce.domain.coupon.CouponRepository;
import kr.hhplus.be.ecommerce.domain.order.OrderCommand;
import kr.hhplus.be.ecommerce.domain.order.OrderInfo.InfoOrder;
import kr.hhplus.be.ecommerce.domain.point.UserPoint;
import kr.hhplus.be.ecommerce.domain.point.UserPointRepository;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductCommand;
import kr.hhplus.be.ecommerce.domain.product.ProductInfo;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.domain.user.User;
import kr.hhplus.be.ecommerce.domain.user.UserRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;

@SpringBootTest
@Transactional
public class OrderServiceIntegrationTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private UserPointRepository userPointRepository;
	
	@Autowired
	private OrderFacade orderFacade;
	
	@Autowired
	private UserRepository userRepository;
	
	private String userId;
	private Integer productId;
	private Integer couponId;
	
	@BeforeEach
	void setUp() {
		
		User user = User.builder()
						.userId("hanghae")
						.address("서울")
						.cdate(LocalDateTime.now())
						.build();
		
		userRepository.save(user);
		userId = user.getUserId();
		
		Product product = Product.builder()
								 .productName("상품1")
								 .productPrice(new BigDecimal("10000"))
								 .stock(10)
								 .cdate(LocalDateTime.now())
								 .productState(1) 
								 .build();

		productRepository.save(product);
		productId = product.getProductId();

		Coupon coupon = Coupon.builder()
							  .couponName("10% 할인 쿠폰")
							  .discount(10)
							  .couponType(CouponEnum.PERCENT.getDesc())
							  .couponStock(10)
							  .couponState(1)
							  .startDate(LocalDateTime.now())
							  .endDate(LocalDateTime.now().plusDays(3))
							  .build();

		couponRepository.save(coupon);
		couponId = coupon.getCouponId();
		
		userPointRepository.save(new UserPoint(userId, new BigDecimal("10000")));
	}

	@Test
	@DisplayName("상품 조회 성공 (상품이 존재할 경우)")
	void successGetProduct() {
		
		ProductCommand.Create command = new ProductCommand.Create(productId);

		ProductInfo.Create result = productService.getProduct(command);

		assertThat(result).isNotNull();
		assertThat(result.getProductId()).isEqualTo(productId);
		assertThat(result.getProductName()).isEqualTo("상품1");
		
	}

	@Test
	@DisplayName("상품 조회 실패 (상품이 없을 경우)")
	void failGetProduct_notFound() {
		
		ProductCommand.Create command = new ProductCommand.Create(99);

		assertThatThrownBy(() -> productService.getProduct(command)).isInstanceOf(CustomException.class)
																	.hasMessageContaining(ErrorEnum.NOT_FOUND_PRODUCT.getMessage());
	}
	
	
	@Test
	@DisplayName("상품 주문 쿠폰 사용")
	void createOrderCoupon() {

		OrderCommand.Create command = OrderCommand.Create.of(userId, couponId, List.of(new CriteriaOrderProduct(productId, 1)));

		InfoOrder result = orderFacade.createOrder(command);

		assertThat(result.getFinalPoint()).isEqualByComparingTo("9000");
		assertThat(result.getRemainingPoint()).isEqualByComparingTo("1000");
		
	}
	
}
