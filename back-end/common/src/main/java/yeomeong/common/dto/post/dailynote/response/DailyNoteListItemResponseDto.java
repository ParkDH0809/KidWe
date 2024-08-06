package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;

@Getter
@NoArgsConstructor
public class DailyNoteListItemResponseDto {
    private Long id;
    private Kid kid;
    private Member writer;
    private LocalDateTime sendTime;

    public DailyNoteListItemResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.kid = dailyNote.getKid();
        this.writer = dailyNote.getWriter();
        this.sendTime = dailyNote.getSendTime();
    }
}
