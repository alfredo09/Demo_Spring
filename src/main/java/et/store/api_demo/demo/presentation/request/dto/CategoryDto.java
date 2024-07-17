package et.store.api_demo.demo.presentation.request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

  private Integer id;
  private String name;
  private String description;

}
