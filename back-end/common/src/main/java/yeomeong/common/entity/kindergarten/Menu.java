package yeomeong.common.entity.kindergarten;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kindergarten kindergarten;

    private String lunch;
    private String lunchAllergies;

    private String snack;
    private String snackAllergies;

    private String dinner;
    private String dinnerAllergies;
    private LocalDate menuDate;


}
