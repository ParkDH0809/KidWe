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
