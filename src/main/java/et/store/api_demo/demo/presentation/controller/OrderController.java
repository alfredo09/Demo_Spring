package et.store.api_demo.demo.presentation.controller;

import et.store.api_demo.demo.data.repository.OrderDetailRepository;
import et.store.api_demo.demo.data.repository.OrderRepository;
import et.store.api_demo.demo.domain.service.interfaces.OrderService;
import et.store.api_demo.demo.domain.entity.Order;
import et.store.api_demo.demo.presentation.request.dto.OrderDto;
import java.util.Optional;
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

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    Order createdOrder = orderService.saveOrder(order);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
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

  @GetMapping("/{orderId}/details")
  public ResponseEntity<OrderDto> getOrderWithDetails(@PathVariable Integer orderId) {
    OrderDto orderDto = orderService.getOrderDtoWithDetails(orderId);
    if (orderDto != null) {
      return ResponseEntity.ok(orderDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}