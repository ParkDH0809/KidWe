package yeomeong.common.dto.post.dailynote.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@NoArgsConstructor
@Getter
public class DailyNoteCommentCreateRequestDto {
    private Long dailynoteId;
    private Long memberId;

    private String content;
    private Long parentCommentId;

    public DailyNoteComment toEntity(DailyNote dailyNote,
                                    Member member,
                                    DailyNoteComment parentComment){
        return DailyNoteComment.builder()
            .dailyNote(dailyNote)
            .member(member)
            .content(this.content)
            .parentComment(parentComment)
            .build();
    }
}
