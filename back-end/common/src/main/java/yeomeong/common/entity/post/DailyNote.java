package yeomeong.common.entity.post;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyNote {
    @Embedded
    private Post post;

    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;
    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    @JoinColumn
    private Long receiverId;
    @JoinColumn
    private Long writerId;

    private Date date;
    private LocalDateTime sendTime;
    private Boolean isDeleted;
}
