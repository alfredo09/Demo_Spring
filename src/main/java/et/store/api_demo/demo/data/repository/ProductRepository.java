package et.store.api_demo.demo.data.repository;

import et.store.api_demo.demo.domain.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  List<Product> findByNameContainingIgnoreCase(String name);
}
