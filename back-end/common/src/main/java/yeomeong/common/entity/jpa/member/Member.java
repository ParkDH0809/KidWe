package yeomeong.common.entity.jpa.member;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import yeomeong.common.entity.jpa.kindergarten.Ban;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY) @Nullable
    private Ban ban;

    @OneToMany @Nullable
    private List<KidMember> kidMember = new ArrayList<>();
}
