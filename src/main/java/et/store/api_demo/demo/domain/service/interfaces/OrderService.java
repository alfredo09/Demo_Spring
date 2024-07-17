package et.store.api_demo.demo.domain.service.interfaces;

import et.store.api_demo.demo.domain.entity.Order;

import et.store.api_demo.demo.domain.entity.OrderDetail;
import et.store.api_demo.demo.presentation.request.dto.OrderDto;
import java.util.List;
import java.util.Optional;

public interface OrderService {
  List<Order> getAllOrders();
  Optional<Order> getOrderById(Integer id);
  Order saveOrder(Order order);
  Order updateOrder(Integer id, Order order);
  void deleteOrder(Integer id);
  List<OrderDetail> getOrderDetails(Integer orderId);
  Order getOrderWithDetails(Integer orderId);
  OrderDto getOrderDtoWithDetails(Integer orderId);
}
