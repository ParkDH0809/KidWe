package yeomeong.common.entity.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import yeomeong.common.entity.kindergarten.Kindergarten;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Kindergarten kindergarten;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bus bus;

    @JsonIgnore
    @OneToMany(mappedBy = "kid")
    @Builder.Default
    private List<KidMember> kidMembers = new ArrayList<>();

}
