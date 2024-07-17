package et.store.api_demo.demo.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@MappedSuperclass
public class Base {

  public boolean active = true;
  public String createdBy;
  public String updatedBy;

  @Column(updatable = false)
  @CreationTimestamp
  public LocalDateTime createdAt;

  @UpdateTimestamp
  public LocalDateTime updatedAt;

}
