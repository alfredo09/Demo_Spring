package et.store.api_demo.demo.presentation.request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreDto {

  private String name;
  private String address;
  private String city;
  private String openingHours;

}
