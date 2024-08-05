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
import yeomeong.common.entity.post.comment.AnnouncementComment;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Entity

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;
    @OneToMany(mappedBy = "dailyNote", cascade = CascadeType.ALL)
    private List<DailyNoteComment> comments;

    private LocalDateTime sendTime;
    private Boolean isDeleted;

}
