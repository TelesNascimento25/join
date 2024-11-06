package service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import exception.ApiException;
import model.Category;
import model.dto.CategoryDTO;
import model.dto.ErrorCode;
import repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
    }

    public CategoryDTO getCategoryStats(Long categoryId) {
        Category category = findCategoryById(categoryId);
        int totalProducts = category.getProducts().size();
        return new CategoryDTO(category.getName(), category.getDescription());
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = findCategoryById(categoryId);
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
        return new CategoryDTO(category.getName(), category.getDescription());
    }

    public void deleteCategory(Long categoryId) {
        Category category = findCategoryById(categoryId);
        categoryRepository.delete(category);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(category.getName(), category.getDescription()))
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = findCategoryById(categoryId);
        return new CategoryDTO(category.getName(), category.getDescription());
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException(ErrorCode.CATEGORY_NOT_FOUND, categoryId));
    }
}
