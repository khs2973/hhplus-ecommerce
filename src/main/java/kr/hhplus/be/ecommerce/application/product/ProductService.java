package kr.hhplus.be.ecommerce.application.product;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductCommand;
import kr.hhplus.be.ecommerce.domain.product.ProductInfo;
import kr.hhplus.be.ecommerce.domain.product.ProductOrderRank;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	// 상품 조회
	public ProductInfo.Create getProduct(ProductCommand.Create commandProduct) {
		
		Product product = productRepository.findByProductId(commandProduct.getProductId())
										   .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_PRODUCT));

		return ProductInfo.Create.of(product.getProductId()
							, product.getProductName()
							, product.getProductPrice()
							, product.getStock());
	}
	
	public ProductInfo.TopRankProductInfo getTopRankProduct(ProductCommand.TopRank topRankCommand) {
		
		int days = topRankCommand.toDays();

		List<ProductOrderRank> topStats = productRepository.findByCdateBetween(days);

		return ProductInfo.TopRankProductInfo.of(topStats);
	}
	
	

}
