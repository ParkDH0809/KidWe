package yeomeong.common.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class KinderGarten {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    private List<Ban> bans = new ArrayList<>();

    private String address;

    private String tel;

    private boolean isVehicle;

    private Date openDate;

}
