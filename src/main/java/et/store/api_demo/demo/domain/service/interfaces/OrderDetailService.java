package et.store.api_demo.demo.domain.service.interfaces;

import et.store.api_demo.demo.domain.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
  List<OrderDetail> getAllOrderDetails();
  Optional<OrderDetail> getOrderDetailById(Integer id);
  OrderDetail saveOrderDetail(OrderDetail orderDetail);
  void deleteOrderDetail(Integer id);
  //OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetail);
}
