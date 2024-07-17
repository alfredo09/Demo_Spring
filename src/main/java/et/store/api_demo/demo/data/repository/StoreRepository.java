package et.store.api_demo.demo.data.repository;

import et.store.api_demo.demo.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {

}
