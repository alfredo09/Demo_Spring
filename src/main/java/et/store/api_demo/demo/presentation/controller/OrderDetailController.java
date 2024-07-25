package et.store.api_demo.demo.presentation.controller;

import et.store.api_demo.demo.domain.entity.OrderDetail;
import et.store.api_demo.demo.domain.service.interfaces.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

  @Autowired
  private OrderDetailService orderDetailService;

  // Obtener todos los detalles de pedidos
  @GetMapping
  public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
    List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
    return ResponseEntity.ok().body(orderDetails);
  }

  // Obtener un detalle de pedido por su ID
  @GetMapping("/{id}")
  public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Integer id) {
    Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
    return orderDetail.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // Crear un nuevo detalle de pedido
  @PostMapping
  public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetail orderDetail) {
    OrderDetail createdOrderDetail = orderDetailService.saveOrderDetail(orderDetail);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDetail);
  }

  // Eliminar un detalle de pedido por su ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer id) {
    orderDetailService.deleteOrderDetail(id);
    return ResponseEntity.noContent().build();
  }
}