package et.store.api_demo.demo.domain.service.implement;

import et.store.api_demo.demo.data.repository.ProductRepository;
import et.store.api_demo.demo.domain.entity.Product;
import et.store.api_demo.demo.domain.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public Optional<Product> getProductById(Integer id) {
    return productRepository.findById(id);
  }

  @Override
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public void deleteProduct(Integer id) {
    productRepository.deleteById(id);
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
}
