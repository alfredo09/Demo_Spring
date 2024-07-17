package et.store.api_demo.demo.domain.service.implement;

import et.store.api_demo.demo.data.repository.OrderRepository;
import et.store.api_demo.demo.data.repository.OrderDetailRepository;
import et.store.api_demo.demo.domain.entity.Order;
import et.store.api_demo.demo.domain.entity.OrderDetail;
import et.store.api_demo.demo.domain.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplement implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @Override
  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  @Override
  public Optional<Order> getOrderById(Integer id) {
    return orderRepository.findById(id);
  }

  @Override
  public Order saveOrder(Order order) {
    return orderRepository.save(order);
  }

  @Override
  public Order updateOrder(Integer id, Order order) {
    Optional<Order> optionalOrder = getOrderById(id);
    if (optionalOrder.isPresent()) {
      Order existingOrder = optionalOrder.get();
      existingOrder.setName(order.getName());
      existingOrder.setDate(order.getDate());
      return orderRepository.save(existingOrder);
    }
    return null;
  }

  @Override
  public void deleteOrder(Integer id) {
    orderRepository.deleteById(id);
  }

  @Override
  public List<OrderDetail> getOrderDetails(Integer orderId) {
    return orderDetailRepository.findByOrderId(orderId);
  }
}
