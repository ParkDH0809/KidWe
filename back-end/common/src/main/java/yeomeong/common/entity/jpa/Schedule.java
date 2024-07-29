package yeomeong.common.entity.jpa;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.kindergarten.Ban;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Schedule {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    private String category;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

}
