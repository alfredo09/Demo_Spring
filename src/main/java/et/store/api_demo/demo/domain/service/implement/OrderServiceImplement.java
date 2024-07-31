package et.store.api_demo.demo.domain.service.implement;

import et.store.api_demo.demo.data.repository.OrderRepository;
import et.store.api_demo.demo.data.repository.OrderDetailRepository;
import et.store.api_demo.demo.data.repository.ProductRepository;
import et.store.api_demo.demo.data.repository.StoreRepository;
import et.store.api_demo.demo.domain.entity.Order;
import et.store.api_demo.demo.domain.entity.OrderDetail;
import et.store.api_demo.demo.domain.entity.Product;
import et.store.api_demo.demo.domain.entity.Store;
import et.store.api_demo.demo.domain.service.interfaces.OrderService;
import et.store.api_demo.demo.presentation.request.dto.OrderDetailDto;
import et.store.api_demo.demo.presentation.request.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplement implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @Autowired
  private StoreRepository storeRepository;

  @Autowired
  private ProductRepository productRepository;

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
    // Guardar la orden primero para obtener el ID generado
    Order savedOrder = orderRepository.save(order);

    // Guardar los detalles del pedido asociados a la orden
    for (OrderDetail orderDetail : order.getOrderDetails()) {
      orderDetailRepository.save(orderDetail);
    }

    return savedOrder;
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

    if (order.getStore() != null) {
      orderDto.setStoreId(order.getStore().getId());
    }

    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
    List<OrderDetailDto> orderDetailDtos = orderDetails.stream()
        .map(orderDetail -> {
          OrderDetailDto detailDto = new OrderDetailDto();
          detailDto.setProductId(orderDetail.getProduct().getId());
          detailDto.setQuantity(orderDetail.getQuantity());

          // Obtener el precio del producto
          Product product = productRepository.findById(orderDetail.getProduct().getId())
              .orElseThrow(() -> new RuntimeException("Product not found"));
          detailDto.setPrice(product.getPrice());

          return detailDto;
        })
        .collect(Collectors.toList());

    orderDto.setOrderDetails(orderDetailDtos);
    return orderDto;
  }

  @Override
  public ResponseEntity<?> createOrder(OrderDto orderDto) {
    Order order = new Order();
    order.setName(orderDto.getName());
    order.setDate(orderDto.getDate());
    order.setShippingAddress(orderDto.getShippingAddress());
    order.setDelivery(orderDto.isDelivery());
    Store store = storeRepository.findById(orderDto.getStoreId()).orElseThrow();
    order.setStore(store);
    List<OrderDetail> orderDetails = orderDto.getOrderDetails().stream()
        .map(detailDto -> {
          OrderDetail orderDetail = new OrderDetail();
          orderDetail.setOrder(order);
          Product product = productRepository.findById(detailDto.getProductId()).orElseThrow();
          orderDetail.setProduct(product);
          orderDetail.setQuantity(detailDto.getQuantity());
          return orderDetail;
        })
        .collect(Collectors.toList());
    order.setOrderDetails(orderDetails);
    Order createdOrder = orderRepository.save(order);

    return ResponseEntity.status(HttpStatus.CREATED).body(
        Collections.singletonMap("orderId", createdOrder.getId()));
  }

}
