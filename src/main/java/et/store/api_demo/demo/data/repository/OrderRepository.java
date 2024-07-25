package et.store.api_demo.demo.data.repository;

import et.store.api_demo.demo.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {



}