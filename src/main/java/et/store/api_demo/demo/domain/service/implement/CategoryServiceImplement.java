package et.store.api_demo.demo.domain.service.implement;

import et.store.api_demo.demo.data.repository.CategoryRepository;
import et.store.api_demo.demo.domain.entity.Category;
import et.store.api_demo.demo.domain.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplement implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public Optional<Category> getCategoryById(Integer id) {
    return categoryRepository.findById(id);
  }

  @Override
  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public void deleteCategory(Integer id) {
    categoryRepository.deleteById(id);
  }

  @Override
  public Category updateCategory(Integer id, Category category) {
    // Obtener la categoría existente por su ID
    Category existingCategory = getCategoryById(id)
        .orElseThrow(() -> new RuntimeException("Category not found"));

    // Actualizar los campos de la categoría existente con los valores de la categoría recibida
    existingCategory.setName(category.getName());
    existingCategory.setDescription(category.getDescription());
    // Actualizar otros campos según sea necesario

    // Guardar y retornar la categoría actualizada
    return categoryRepository.save(existingCategory);
  }

}
