package et.store.api_demo.demo.domain.service.interfaces;

import et.store.api_demo.demo.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
  List<Product> getAllProducts();
  Optional<Product> getProductById(Integer id);
  Product saveProduct(Product product);
  void deleteProduct(Integer id);
  List<Product> searchProductsByName(String name);
  Product updateProduct(Integer id, Product product);
}
