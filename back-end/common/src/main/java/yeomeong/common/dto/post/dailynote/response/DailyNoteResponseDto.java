package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;

@Getter
@NoArgsConstructor
public class DailyNoteResponseDto {
    private Long id;

    private Post post;

    private Kid kid;

    private Member writer;

    private LocalDateTime sendTime;

    public DailyNoteResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.post = dailyNote.getPost();
        this.kid = dailyNote.getKid();
        this.writer = dailyNote.getWriter();
        this.sendTime = dailyNote.getSendTime();
    }
}
