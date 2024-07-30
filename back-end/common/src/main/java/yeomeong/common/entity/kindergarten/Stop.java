package yeomeong.common.entity.kindergarten;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stop {

    @Id @GeneratedValue
    private Long id;

    private double latitude;

    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bus bus;
}
