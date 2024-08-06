package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteCommentResponseDto {
    private Long id;

    private MemberProfileResponseDto member;

    private String content;

    private DailyNoteCommentResponseDto parentComment;

    private List<DailyNoteCommentResponseDto> replies;

    private LocalDateTime updatedAt;

    public DailyNoteCommentResponseDto(DailyNoteComment dailyNoteComment) {
        this.id = dailyNoteComment.getId();
        this.member = MemberProfileResponseDto.toMemberProfileDto(dailyNoteComment.getMember());
        this.content = dailyNoteComment.getContent();
        this.parentComment = new DailyNoteCommentResponseDto(dailyNoteComment.getParentComment());
        this.replies = new ArrayList<>();
        for(DailyNoteComment replie : dailyNoteComment.getReplies()) {
            this.replies.add(new DailyNoteCommentResponseDto(replie));
        }
        this.updatedAt = dailyNoteComment.getUpdatedAt();
    }
}
