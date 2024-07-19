package yeomeong.common.entity.member;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.kindergarten.Ban;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String name;

    private String tel;

    @Enumerated(EnumType.STRING)
    private rtype role; //[DIRECTOR, TEACHER, GUARDIAN ]

    @Enumerated(EnumType.STRING)
    private atype memberStatus; //ACCEPT, DECLINE, PENDING

    @ManyToOne(fetch = FetchType.LAZY) @Nullable
    private Ban ban;

    @OneToMany @Nullable
    private List<KidMember> kidMember = new ArrayList<>();
}
