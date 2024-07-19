package yeomeong.common.entity.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;

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

    @JoinColumn(name = "member_id")
    private Long receiverId;

    @JoinColumn(name = "member_id")
    private Long writerId;
}
