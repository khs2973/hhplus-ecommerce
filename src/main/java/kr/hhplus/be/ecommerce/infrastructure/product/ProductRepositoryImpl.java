package kr.hhplus.be.ecommerce.infrastructure.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.hhplus.be.ecommerce.domain.order.QOrder;
import kr.hhplus.be.ecommerce.domain.order.QOrderProduct;
import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductOrderRank;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.domain.product.QProduct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{
	
	private final ProductJpaRepository productJpaRepository;
	private final JPAQueryFactory queryFactory;
	
	QOrderProduct orderProduct = QOrderProduct.orderProduct;
	QProduct product = QProduct.product;
	QOrder order = QOrder.order;
	
	@Override
	public Optional<Product> findByProductId(Integer productId) {
		return productJpaRepository.findById(productId);
	}
	
	@Override
	public void save(Product product) {
		productJpaRepository.save(product);
	}
	
	@Override
 	public Optional<Product> findByProductIdForUpdate(Integer productId) {
 		return productJpaRepository.findByProductIdForUpdate(productId);
 	}
	
	@Override
	public void saveAndFlush(Product product) {
		productJpaRepository.saveAndFlush(product);
	}
	
	@Override
	public List<ProductOrderRank> findByCdateBetween(int days) {
		return queryFactory.select(Projections.constructor(ProductOrderRank.class
														 , product.productId
														 , product.productName
														 , orderProduct.orderQuantity.sum()))
						   .from(orderProduct)
						   .join(product)
						   .on(orderProduct.productId.eq(product.productId))
						   .join(order)
						   .on(orderProduct.orderId.eq(order.orderId))
						   .where(order.cdate.goe(LocalDateTime.now().minusDays(days)))
						   .groupBy(product.productId, product.productName)
						   .orderBy(orderProduct.orderQuantity.sum().desc())
						   .limit(10)
						   .fetch();
	}
}
