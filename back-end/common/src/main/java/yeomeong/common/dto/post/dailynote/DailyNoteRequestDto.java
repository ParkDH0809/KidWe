package yeomeong.common.dto.post.dailynote;

import java.time.LocalDateTime;
import lombok.Data;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;

@Data
public class DailyNoteRequestDto {
    private Post post;

    private Long kidId;
    private Long writerId;

    private LocalDateTime sendTime;

    public DailyNote toEntity(){
        return DailyNote.builder()
            .post(this.post)
            .build();
    }
}