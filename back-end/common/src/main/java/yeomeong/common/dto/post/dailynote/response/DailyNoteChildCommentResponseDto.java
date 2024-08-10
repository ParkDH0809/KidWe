package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteChildCommentResponseDto {
    private Long id;

    private MemberProfileResponseDto member;

    private final static String deletedMessage = "삭제된 댓글입니다";
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updateTime;

    public DailyNoteChildCommentResponseDto(DailyNoteComment dailyNoteComment) {
        this.id = dailyNoteComment.getId();
        this.member = MemberProfileResponseDto.toMemberProfileDto(dailyNoteComment.getMember());
        if(dailyNoteComment.getIsDeleted()) {
            this.content = deletedMessage;
        }
        else{
            this.content = dailyNoteComment.getContent();
        }
        this.updateTime = dailyNoteComment.getUpdatedAt();
    }
}
