package kr.hhplus.be.ecommerce.interfaces.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.ecommerce.application.product.ProductService;
import kr.hhplus.be.ecommerce.domain.product.ProductCommand;
import kr.hhplus.be.ecommerce.domain.product.ProductInfo.InfoProduct;
import kr.hhplus.be.ecommerce.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private final ProductService productService;
	
	@GetMapping("/product")
	public ApiResponse<?> getProduct(ProductRequest productRequest) {
		
		ProductCommand.Create productCommand = productRequest.toProductCommand();
		
		InfoProduct infoProduct = productService.getProduct(productCommand);
		
		return ApiResponse.success(ProductResponse.from(infoProduct));

	}
}
