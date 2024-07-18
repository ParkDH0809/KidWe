package yeomeong.common.entity.member;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.post.DailyNote;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private rtype rtype; //[DIRECTOR, TEACHER, GUARDIAN ]

    @OneToMany
    private List<DailyNote> dailyNotes = new ArrayList<>();
}
