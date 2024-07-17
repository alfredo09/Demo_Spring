package et.store.api_demo.demo.presentation.controller;

import et.store.api_demo.demo.domain.entity.Category;
import et.store.api_demo.demo.domain.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  // Obtener todas las categorías
  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryService.getAllCategories();
    return ResponseEntity.ok().body(categories);
  }

  // Obtener una categoría por su ID
  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
    Optional<Category> category = categoryService.getCategoryById(id);
    return category.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // Crear una nueva categoría
  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    Category createdCategory = categoryService.saveCategory(category);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
  }

  // Actualizar una categoría existente
  @PutMapping("/{id}")
  public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
    Category updatedCategory = categoryService.updateCategory(id, category);
    return ResponseEntity.ok().body(updatedCategory);
  }

  // Eliminar una categoría por su ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
