package et.store.api_demo.demo.presentation.responsePojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailPojo {

  private Integer id;
  private Integer productId;
  private Integer quantity;


}
