package et.store.api_demo.demo.domain.service.implement;

import et.store.api_demo.demo.data.repository.OrderDetailRepository;
import et.store.api_demo.demo.domain.entity.OrderDetail;
import et.store.api_demo.demo.domain.service.interfaces.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImplement implements OrderDetailService {

  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @Override
  public List<OrderDetail> getAllOrderDetails() {
    return orderDetailRepository.findAll();
  }

  @Override
  public Optional<OrderDetail> getOrderDetailById(Integer id) {
    return orderDetailRepository.findById(id);
  }

  @Override
  public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
    return orderDetailRepository.save(orderDetail);
  }

  @Override
  public void deleteOrderDetail(Integer id) {
    orderDetailRepository.deleteById(id);
  }
//  @Override
//  public OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetail) {
//    // Obtener el detalle de pedido existente por su ID
//    OrderDetail existingOrderDetail = getOrderDetailById(id)
//        .orElseThrow(() -> new RuntimeException("OrderDetail not found"));
//
//    // Actualizar los campos del detalle de pedido existente con los valores del detalle de pedido recibido
//    existingOrderDetail.setProductId(orderDetail.getProductId());
//    existingOrderDetail.setOrderId(orderDetail.getOrderId());
//    existingOrderDetail.setQuantity(orderDetail.getQuantity());
//    // Actualizar otros campos según sea necesario
//
//    // Guardar y retornar el detalle de pedido actualizado
//    return orderDetailRepository.save(existingOrderDetail);
//  }

}
