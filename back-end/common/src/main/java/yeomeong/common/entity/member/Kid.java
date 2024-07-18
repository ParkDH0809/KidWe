package yeomeong.common.entity.member;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.Ban;
import yeomeong.common.entity.Bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Kid {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bus bus;

    @OneToMany(mappedBy = "kid")
    private List<KidGuardian> kidGuardians = new ArrayList<>();

    private String name;

    @Enumerated(EnumType.STRING)
    private gtype gender; //MALE , FEMALE

    private double tall;

    private double weight;

    private Date birthday;

    private String allergies;

    private Date startAttendanceDate;

    private String picture;
}
