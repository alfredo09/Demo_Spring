package et.store.api_demo.demo.presentation.controller;

import et.store.api_demo.demo.domain.entity.Category;
import et.store.api_demo.demo.domain.entity.Product;
import et.store.api_demo.demo.domain.service.interfaces.CategoryService;
import et.store.api_demo.demo.domain.service.interfaces.ProductService;
import et.store.api_demo.demo.presentation.request.dto.ProductDto;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryService categoryService;

  // Obtener todos los productos
  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    return ResponseEntity.ok().body(products);
  }

  // Obtener un producto por su ID
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
    Optional<Product> product = productService.getProductById(id);
    return product.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // Crear un nuevo producto
  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
    System.out.println("Received Product DTO: " + productDto);

    Product product = new Product();
    product.setName(productDto.getName());
    product.setDescription(productDto.getDescription());
    product.setPrice(productDto.getPrice());
    product.setStock(productDto.getStock());
    product.setActive(true); // Set the product as active by default

    if (productDto.getCategoryId() != null) {
      Optional<Category> categoryOpt = categoryService.getCategoryById(productDto.getCategoryId());
      if (categoryOpt.isPresent()) {
        product.setCategory(categoryOpt.get());
      } else {
        System.out.println("Category not found: " + productDto.getCategoryId());
        return ResponseEntity.badRequest().body(null); // Handle error: category not found
      }
    } else {
      System.out.println("CategoryId is null");
      return ResponseEntity.badRequest().body(null); // Handle error: categoryId not provided
    }

    try {
      Product createdProduct = productService.saveProduct(product);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle other errors
    }
  }

  // Actualizar un producto existente
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
    Product updatedProduct = productService.updateProduct(id, product);
    return ResponseEntity.ok().body(updatedProduct);
  }

  // Eliminar un producto por su ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam String name) {
    List<ProductDto> products = productService.searchProductsByName(name).stream()
        .map(this::convertToDto)  // Convertir Product a ProductDto
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(products);
  }

  @GetMapping("/pageable")
  public ResponseEntity<Page<Product>> getProductsPage(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortOrder) {

    Page<Product> productPage = productService.getProductsPage(page, size, sortBy, sortOrder);
    return ResponseEntity.ok().body(productPage);
  }

  private ProductDto convertToDto(Product product) {
    ProductDto dto = new ProductDto();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setStock(product.getStock());
    dto.setPrice(product.getPrice());

    if (product.getCategory() != null) {
      dto.setCategoryId(product.getCategory().getId());
    }
    return dto;
  }
}