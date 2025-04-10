package kr.hhplus.be.ecommerce.interfaces.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.ecommerce.application.product.ProductService;
import kr.hhplus.be.ecommerce.domain.product.Product;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@GetMapping("/product")
	public Product getProduct(ProductRequest productRequest) {
		return productService.getProduct(productRequest);
	}
}
