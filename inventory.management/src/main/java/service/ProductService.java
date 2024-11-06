package service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import exception.ApiException;
import model.Category;
import model.Products;
import model.dto.ErrorCode;
import model.dto.ProductsDTO;
import repository.CategoryRepository;
import repository.ProductsRepository;

@Service
public class ProductService {

	private final ProductsRepository productRepository;
	private final CategoryRepository categoryRepository;

	public ProductService(ProductsRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	public void createProduct(ProductsDTO productsDTO) {
		Category category = categoryRepository.findById(productsDTO.getCategoryId())
				.orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND, productsDTO.getCategoryId()));

		Products product = new Products();
		product.setName(productsDTO.getName());
		product.setDescription(productsDTO.getDescription());
		product.setPrice(productsDTO.getPrice());
		product.setCategory(category);

		productRepository.save(product);
	}

	public ProductsDTO getProductDetails(Long productId) {
		Products product = productRepository.findById(productId)
				.orElseThrow(() -> new ApiException(ErrorCode.PRODUCT_NOT_FOUND, productId));

		return new ProductsDTO(product.getName(), product.getDescription(), product.getPrice(),
				product.getCategory().getId());
	}

	public List<ProductsDTO> getProductsByCategory(Long categoryId) {
		List<Products> products = productRepository.findByCategoryId(categoryId);

		if (products.isEmpty()) {
			throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND, categoryId);
		}

		return products.stream().map(product -> new ProductsDTO(product.getName(), product.getDescription(),
				product.getPrice(), product.getCategory().getId())).collect(Collectors.toList());
	}

	public boolean updateProduct(Long productId, ProductsDTO productsDTO) {
		Products product = productRepository.findById(productId)
				.orElseThrow(() -> new ApiException(ErrorCode.PRODUCT_NOT_FOUND, productId));

		product.setName(productsDTO.getName());
		product.setDescription(productsDTO.getDescription());
		product.setPrice(productsDTO.getPrice());

		Category category = categoryRepository.findById(productsDTO.getCategoryId())
				.orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND, productsDTO.getCategoryId()));
		product.setCategory(category);

		productRepository.save(product);
		return true;
	}

	public boolean deleteProduct(Long productId) {
		Products product = productRepository.findById(productId)
				.orElseThrow(() -> new ApiException(ErrorCode.PRODUCT_NOT_FOUND, productId));

		productRepository.delete(product);
		return true;
	}
}
