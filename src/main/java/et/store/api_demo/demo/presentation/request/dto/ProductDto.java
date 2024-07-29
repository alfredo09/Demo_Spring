package et.store.api_demo.demo.presentation.request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

  private Integer id;
  private String name;
  private String description;
  private int stock;
  private double price;
  private Integer categoryId;
  private boolean active;
}
