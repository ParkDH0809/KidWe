package yeomeong.common.entity.jpa.kindergarten;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Menu {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kindergarten kinderGarten;

    private String lunch;
    private String lunchAllergies;

    private String snack;
    private String snackAllergies;

    private String dinner;
    private String dinnerAllergies;

    private Date menuDate;
}
