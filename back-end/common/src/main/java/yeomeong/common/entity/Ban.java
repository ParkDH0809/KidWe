package yeomeong.common.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ban {

    @Id @GeneratedValue
    @JoinColumn(name = "ban_id")
    private Long id;

    private String name;

    @ManyToOne
    private KinderGarten kinderGarten;

}
