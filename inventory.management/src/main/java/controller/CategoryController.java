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
import model.dto.CategoryDTO;
import service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
		categoryService.createCategory(categoryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/{categoryId}/stats")
	public ResponseEntity<CategoryDTO> getCategoryStats(@PathVariable Long categoryId) {
		CategoryDTO categoryDTO = categoryService.getCategoryStats(categoryId);
		return ResponseEntity.ok(categoryDTO);
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,
			@RequestBody @Valid CategoryDTO categoryDTO) {
		CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> categories = categoryService.getAllCategories();
		if (categories.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) {
		CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(categoryDTO);
	}
}