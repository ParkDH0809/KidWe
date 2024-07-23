package yeomeong.common.entity.jpa.kindergarten;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Bus {

    @Id @GeneratedValue
    private Long id;

    private String carNumber;
}
