package yeomeong.common.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class KinderGarten {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    private List<Ban> bans = new ArrayList<>();

}
