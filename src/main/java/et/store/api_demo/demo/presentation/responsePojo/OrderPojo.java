package et.store.api_demo.demo.presentation.responsePojo;

import et.store.api_demo.demo.presentation.request.dto.OrderDetailDto;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderPojo {

  private Integer id;
  private StorePojo store;
  String name;
  String date;
  String shippingAddress;
  Boolean delivery;

  private List<OrderDetailPojo> orderDetailList;


}
