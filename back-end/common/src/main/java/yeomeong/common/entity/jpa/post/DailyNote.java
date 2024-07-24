package yeomeong.common.entity.jpa.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.kindergarten.Ban;
import yeomeong.common.entity.jpa.member.Kid;
import yeomeong.common.entity.jpa.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class DailyNote {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "dailyNote")
    private List<Memo> memos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    @Embedded
    private Post post;

    @JoinColumn
    private Long receiverId;

    @JoinColumn
    private Long writerId;
}
