package et.store.api_demo.demo.presentation.request.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

  private String name;
  private LocalDate date;
  private String shippingAddress;
  private boolean isDelivery;
  private Integer storeId;
  private List<OrderDetailDto> orderDetails;
}
