package kr.hhplus.be.ecommerce.application.product;

import org.springframework.stereotype.Service;

import kr.hhplus.be.ecommerce.domain.product.Product;
import kr.hhplus.be.ecommerce.domain.product.ProductCommand;
import kr.hhplus.be.ecommerce.domain.product.ProductInfo.InfoProduct;
import kr.hhplus.be.ecommerce.domain.product.ProductRepository;
import kr.hhplus.be.ecommerce.interfaces.common.CustomException;
import kr.hhplus.be.ecommerce.interfaces.common.ErrorEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	// 상품 조회
	public InfoProduct getProduct(ProductCommand.Create commandProduct) {
		
		Product product = productRepository.findByProductId(commandProduct.getProductId())
										   .orElseThrow(() -> new CustomException(ErrorEnum.NOT_FOUND_PRODUCT));

		return InfoProduct.of(product.getProductId()
							, product.getProductName()
							, product.getProductPrice()
							, product.getStock());
	}
	

}
