package yeomeong.common.entity.jpa.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.kindergarten.Ban;
import yeomeong.common.entity.jpa.post.Announcement;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<KidMember> kidMember = new ArrayList<>();
}
