package yeomeong.common.dto.post.dailynote.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteCommentRequestDto {
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
