package et.store.api_demo.demo.presentation.controller;

import et.store.api_demo.demo.data.repository.OrderDetailRepository;
import et.store.api_demo.demo.data.repository.OrderRepository;
import et.store.api_demo.demo.domain.entity.OrderDetail;
import et.store.api_demo.demo.domain.entity.Product;
import et.store.api_demo.demo.domain.entity.Store;
import et.store.api_demo.demo.domain.service.interfaces.OrderService;
import et.store.api_demo.demo.domain.entity.Order;
import et.store.api_demo.demo.domain.service.interfaces.ProductService;
import et.store.api_demo.demo.domain.service.interfaces.StoreService;
import et.store.api_demo.demo.presentation.request.dto.OrderDetailDto;
import et.store.api_demo.demo.presentation.request.dto.OrderDto;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @Autowired
  private StoreService storeService;
  @Autowired
  private ProductService productService; // Debes tener un servicio para manejar las operaciones de producto

  @PostMapping
  public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
    return orderService.createOrder(orderDto);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) {
    Optional<Order> optionalOrder = orderService.getOrderById(orderId);
    return optionalOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<Order> updateOrder(@PathVariable Integer orderId, @RequestBody Order order) {
    Order updatedOrder = orderService.updateOrder(orderId, order);
    if (updatedOrder == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedOrder);
  }

  @GetMapping("details/{orderId}")
  public ResponseEntity<List<OrderDetailDto>> getOrderDetails(@PathVariable Integer orderId) {
    List<OrderDetailDto> orderDetails = orderService.getOrderDtoWithDetails(orderId).getOrderDetails();

    if (orderDetails != null && !orderDetails.isEmpty()) {
      return ResponseEntity.ok(orderDetails);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    List<Order> orders = orderService.getAllOrders(); // Implementa este método en tu servicio
    if (orders != null && !orders.isEmpty()) {
      return ResponseEntity.ok(orders);
    } else {
      return ResponseEntity.noContent().build(); // O usa ResponseEntity.notFound().build() si es más apropiado
    }
  }

}