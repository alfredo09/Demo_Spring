package et.store.api_demo.demo.data.repository;

import et.store.api_demo.demo.domain.entity.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

  List<OrderDetail> findByOrderId(Integer orderId);
}
