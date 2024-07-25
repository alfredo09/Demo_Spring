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
import et.store.api_demo.demo.presentation.responsePojo.OrderDetailPojo;
import et.store.api_demo.demo.presentation.responsePojo.OrderPojo;
import et.store.api_demo.demo.presentation.responsePojo.StorePojo;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  @Override
  public ResponseEntity<?> createOrder(OrderDto orderDto) {
    // Crear una nueva instancia de Order y configurar sus campos
    Order order = new Order();
    order.setName(orderDto.getName());
    order.setDate(orderDto.getDate());
    order.setShippingAddress(orderDto.getShippingAddress());
    order.setDelivery(orderDto.isDelivery());

    // Obtener la tienda desde el servicio de tienda
    Store store = storeRepository.findById(orderDto.getStoreId()).orElseThrow();



    // Asignar los datos de la tienda a la orden
    order.setStore(store);

    // Mapear los detalles del pedido (OrderDetailDto) a entidades OrderDetail
    List<OrderDetail> orderDetails = orderDto.getOrderDetails().stream()
        .map(detailDto -> {
          OrderDetail orderDetail = new OrderDetail();
          orderDetail.setOrder(order); // Establecer la relación con la orden

          // Obtener el producto desde el servicio de producto
          Product product = productRepository.findById(detailDto.getProductId()).orElseThrow();

          orderDetail.setProduct(product);
          orderDetail.setQuantity(detailDto.getQuantity());
          return orderDetail;
        })
        .collect(Collectors.toList());

    // Asignar los detalles del pedido a la orden
    order.setOrderDetails(orderDetails);

    // Guardar la orden completa (incluidos los detalles) utilizando el servicio
    Order createdOrder = orderRepository.save(order);

    OrderPojo orderPojo = new OrderPojo();
    orderPojo.setId(order.getId());
    orderPojo.setName(order.getName());
    orderPojo.setDate(order.getDate().toString());
    orderPojo.setShippingAddress(order.getShippingAddress());
    orderPojo.setDelivery(order.isDelivery());

    StorePojo storePojo = new StorePojo();
    storePojo.setId(store.getId());
    storePojo.setName(store.getName());
    storePojo.setCity(store.getCity());
    storePojo.setOpeningHours(store.getOpeningHours());

    orderPojo.setStore(storePojo);

    List<OrderDetailPojo> orderDetailPojoList = new ArrayList<>();

    List<OrderDetail> orderDetailList = order.getOrderDetails();
    for ( OrderDetail orderDetail : orderDetailList ){
      OrderDetailPojo orderDetailPojo = new OrderDetailPojo();
      orderDetailPojo.setId(orderDetail.getId());
      orderDetailPojo.setQuantity(orderDetail.getQuantity());
      orderDetailPojo.setProductId(orderDetail.getProduct().getId());
      orderDetailPojoList.add(orderDetailPojo);
    }


    orderPojo.setOrderDetailList(orderDetailPojoList);


    // Devolver la respuesta con la orden creada, incluyendo los datos de la tienda y los productos
    return ResponseEntity.status(HttpStatus.CREATED).body(orderPojo);
  }
}