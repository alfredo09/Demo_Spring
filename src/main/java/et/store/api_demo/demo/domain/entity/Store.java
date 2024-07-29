package et.store.api_demo.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stores")
public class Store extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String city;

  @Column(name = "opening_hours")
  private String openingHours;

  @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
  @JsonIgnore // Ignora la lista de órdenes en la serialización JSON
  private List<Order> orders;
}
