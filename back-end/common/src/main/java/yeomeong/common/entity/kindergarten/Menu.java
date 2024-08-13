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

    private boolean isDeleted;


    public Menu(String lunch, String lunchAllergies, String snack, String snackAllergies, String dinner, String dinnerAllergies) {
        this.lunch = lunch;
        this.lunchAllergies = lunchAllergies;
        this.snack = snack;
        this.snackAllergies = snackAllergies;
        this.dinner = dinner;
        this.dinnerAllergies = dinnerAllergies;
    }

    public Menu updateMenu(
            String lunch,
            String lunchAllergies,
            String snack,
            String snackAllergies,
            String dinner,
            String dinnerAllergies
    ){
       return new Menu(this.lunch = lunch,
        this.lunchAllergies = lunchAllergies,
        this.snack = snack,
        this.snackAllergies = snackAllergies,
        this.dinner = dinner,
        this.dinnerAllergies = dinnerAllergies);
    }


}
