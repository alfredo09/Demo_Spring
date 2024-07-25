package et.store.api_demo.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class Order extends Base {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate date;

  @Column(name = "shipping_address")
  private String shippingAddress;

  @Column(name = "is_delivery")
  private boolean delivery;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<OrderDetail> orderDetails;

  public void addOrderDetail(OrderDetail orderDetail) {
    orderDetail.setOrder(this);
    this.orderDetails.add(orderDetail);
  }

  public void removeOrderDetail(OrderDetail orderDetail) {
    orderDetail.setOrder(null);
    this.orderDetails.remove(orderDetail);
  }
}
