package et.store.api_demo.demo.domain.service.implement;

import et.store.api_demo.demo.data.repository.OrderRepository;
import et.store.api_demo.demo.data.repository.OrderDetailRepository;
import et.store.api_demo.demo.domain.entity.Order;
import et.store.api_demo.demo.domain.entity.OrderDetail;
import et.store.api_demo.demo.domain.service.interfaces.OrderService;
import et.store.api_demo.demo.presentation.request.dto.OrderDetailDto;
import et.store.api_demo.demo.presentation.request.dto.OrderDto;
import java.util.stream.Collectors;
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

  @Override
  public Order getOrderWithDetails(Integer orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
    order.setOrderDetails(orderDetails);
    return order;
  }

  @Override
  public OrderDto getOrderDtoWithDetails(Integer orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

    OrderDto orderDto = new OrderDto();
    orderDto.setName(order.getName());
    orderDto.setDate(order.getDate());
    orderDto.setShippingAddress(order.getShippingAddress());
    orderDto.setDelivery(order.isDelivery());

    // Aquí obtienes el ID de la tienda desde la relación
    if (order.getStore() != null) {
      orderDto.setStoreId(order.getStore().getId());
    }

    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
    List<OrderDetailDto> orderDetailDtos = orderDetails.stream()
        .map(orderDetail -> {
          OrderDetailDto detailDto = new OrderDetailDto();

          // Obtener el productId a través de la relación con Product
          if (orderDetail.getProduct() != null) {
            detailDto.setProductId(orderDetail.getProduct().getId());
          }

          detailDto.setQuantity(orderDetail.getQuantity());
          return detailDto;
        })
        .collect(Collectors.toList());

    orderDto.setOrderDetails(orderDetailDtos);

    return orderDto;
  }
}
