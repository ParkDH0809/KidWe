package yeomeong.common.dto.post.dailynote.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteCommentUpdateRequestDto {
    private Long id;

    private String content;

    public DailyNoteComment toEntity(DailyNote dailyNote,
        Member member,
        DailyNoteComment parentComment,
        List<DailyNoteComment> replies){
        return DailyNoteComment.builder()
            .id(this.id)
            .dailyNote(dailyNote)
            .member(member)
            .content(this.content)
            .parentComment(parentComment)
            .replies(replies).build();
    }
}