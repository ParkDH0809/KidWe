package yeomeong.common.dto.post.dailynote.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;

@Getter
public class DailyNoteUpdateRequestDto {
    private Long id;

    private Post post;

    private Long kidId;
    private Long writerId;

    private LocalDateTime sendTime;
}
