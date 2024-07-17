package et.store.api_demo.demo.data.repository;

import et.store.api_demo.demo.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
