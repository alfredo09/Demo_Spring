package et.store.api_demo.demo.presentation.responsePojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StorePojo {

  private Integer id;
  private String name;
  private String city;
  private String openingHours;

}
