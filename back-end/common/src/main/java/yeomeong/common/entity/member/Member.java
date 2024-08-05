package yeomeong.common.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import yeomeong.common.dto.member.MemberUpdateRequestDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String tel;

    @Enumerated(EnumType.STRING)
    private rtype role; //[ROLE_DIRECTOR, ROLE_TEACHER, ROLE_GUARDIAN ]

    @Enumerated(EnumType.STRING)
    private atype memberStatus; //ACCEPT, DECLINE, PENDING

    private String picture;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kindergarten kindergarten;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<KidMember> kidMember = new ArrayList<>();

    @ColumnDefault("false")
    private Boolean isDeleted;

    public void updateFromDto(MemberUpdateRequestDto dto) {
        this.email = dto.getName() != null ? dto.getName() : this.name;
        this.tel = dto.getTel() != null ? dto.getTel() : this.tel;
        this.picture = dto.getPicture() != null ? dto.getPicture() : this.picture;
    }

}
