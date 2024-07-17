package et.store.api_demo.demo.domain.service.interfaces;

import et.store.api_demo.demo.domain.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
  List<Category> getAllCategories();
  Optional<Category> getCategoryById(Integer id);
  Category saveCategory(Category category);
  void deleteCategory(Integer id);
  Category updateCategory(Integer id, Category category);
}
