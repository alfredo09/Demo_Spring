package et.store.api_demo.demo.domain.service.implement;

import et.store.api_demo.demo.data.repository.ProductRepository;
import et.store.api_demo.demo.domain.entity.Product;
import et.store.api_demo.demo.domain.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public Optional<Product> getProductById(Integer productId) {
    return productRepository.findById(productId);
  }

  @Override
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public void deleteProduct(Integer id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found"));
    product.setActive(false);  // Cambia el estado a inactivo en lugar de eliminar el producto
    productRepository.save(product);
  }

  @Override
  public List<Product> searchProductsByName(String name) {
    return productRepository.findByNameContainingIgnoreCase(name);
  }

  @Override
  public Product updateProduct(Integer id, Product product) {
    Product existingProduct = getProductById(id)
        .orElseThrow(() -> new RuntimeException("Product not found"));
    existingProduct.setName(product.getName());
    existingProduct.setDescription(product.getDescription());
    existingProduct.setPrice(product.getPrice());
    return productRepository.save(existingProduct);
  }

  @Override
  public Page<Product> getProductsPage(int page, int size, String sortBy, String sortOrder) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
    return productRepository.findAll(pageable);
  }
}