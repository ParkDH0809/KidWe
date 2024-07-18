package yeomeong.common.entity.member;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Guardian extends Member{

    private String tel;

    private boolean alarm;

    @OneToMany
    private List<KidGuardian> kidGuardian = new ArrayList<>();


}
