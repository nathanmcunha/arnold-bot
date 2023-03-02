/* (C) 2023 */
package br.com.nathancunha.arnold.bot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
public class Warrior {
  @Id private Long id;
  private String firstName;

  @OneToMany(mappedBy = "warrior")
  private Set<Attendance> attendance;
}
