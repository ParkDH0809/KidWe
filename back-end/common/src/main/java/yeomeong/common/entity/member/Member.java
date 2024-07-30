package yeomeong.common.entity.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import yeomeong.common.entity.kindergarten.Ban;

import java.util.ArrayList;
import java.util.List;
import yeomeong.common.entity.kindergarten.Kindergarten;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String name;

    private String tel;

    @Enumerated(EnumType.STRING)
    private rtype role; //[ROLE_DIRECTOR, ROLE_TEACHER, ROLE_GUARDIAN ]

    @Enumerated(EnumType.STRING)
    private atype memberStatus; //ACCEPT, DECLINE, PENDING

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kindergarten kindergarten;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<KidMember> kidMember = new ArrayList<>();
}
