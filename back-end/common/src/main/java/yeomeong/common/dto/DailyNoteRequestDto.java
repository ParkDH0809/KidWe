package yeomeong.common.dto;

import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.Post;

public class DailyNoteRequestDto {
    private Post post;

    private Long id;
    private Member member;

    private Ban ban;
    private Kid kid;

    private Long receiverId;
    private Long writerId;

    private LocalDateTime sendTime;
    private Boolean isDeleted;

}
