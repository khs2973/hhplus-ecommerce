package kr.hhplus.be.ecommerce.application.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import kr.hhplus.be.ecommerce.interfaces.product.ProductRequest;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	// 상품 조회
	public Product getProduct(ProductRequest productRequest) {
		
		Product product = productRepository.findByProductIdAndState(productRequest.getProductId(), 0)
										   .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_PRODUCT));

		return product;
	}
	

}
