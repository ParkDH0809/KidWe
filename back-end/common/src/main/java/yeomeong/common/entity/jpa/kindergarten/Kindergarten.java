package yeomeong.common.entity.jpa.kindergarten;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Kindergarten {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    private Bus bus;

    @OneToMany(mappedBy = "kindergarten")
    private List<Ban> bans = new ArrayList<>();

    private String address;

    private String tel;

    private boolean isVehicle;

    private Date openDate;

}
