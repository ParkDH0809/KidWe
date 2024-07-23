package yeomeong.common.entity.jpa.member;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.kindergarten.Ban;
import yeomeong.common.entity.jpa.kindergarten.Bus;

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

    private String name;

    private Date birthday;

    private Date startAttendanceDate;

    private String picture;

    private String allergies;

    @Enumerated(EnumType.STRING)
    private gtype gender; //MALE , FEMALE

    private double tall;

    private double weight;

    private boolean isTake;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bus bus;

    @OneToMany(mappedBy = "kid")
    private List<KidMember> kidMembers = new ArrayList<>();





}
