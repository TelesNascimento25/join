package controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import model.dto.ProductsDTO;
import service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductsDTO productDTO) {
        productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build(); 
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductsDTO> getProductDetails(@PathVariable Long productId) {
        ProductsDTO productDTO = productService.getProductDetails(productId); 
        return ResponseEntity.ok(productDTO); 
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductsDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductsDTO> products = productService.getProductsByCategory(categoryId);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products); 
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductsDTO productDTO) {
        boolean updated = productService.updateProduct(productId, productDTO); 
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = productService.deleteProduct(productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}