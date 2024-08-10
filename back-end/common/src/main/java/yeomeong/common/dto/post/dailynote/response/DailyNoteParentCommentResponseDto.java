package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.kid.KidSummaryResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.TeacherSummaryResponseDto;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteParentCommentResponseDto {
    private Long id;

    private TeacherSummaryResponseDto teacher;
    private KidSummaryResponseDto kid;

    private Boolean isDeleted;
    @JsonIgnore
    private final static String deletedMessage = "삭제된 댓글입니다";

    private String content;
    private List<DailyNoteChildCommentResponseDto> childs;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;

    public DailyNoteParentCommentResponseDto(DailyNoteComment dailyNoteComment) {
        this.id = dailyNoteComment.getId();
        if(dailyNoteComment.getMember().getRole() == rtype.ROLE_GUARDIAN) {
            this.teacher = null;
            this.kid = new KidSummaryResponseDto(dailyNoteComment.getDailyNote().getKid());
        }
        else{
            this.teacher = new TeacherSummaryResponseDto(dailyNoteComment.getMember());
            this.kid = null;
        }

        if(dailyNoteComment.getIsDeleted()) {
            this.isDeleted = true;
            this.content = deletedMessage;
        }
        else{
            this.isDeleted = false;
            this.content = dailyNoteComment.getContent();
        }

        childs = new ArrayList<>();
        for(DailyNoteComment dailyNoteChildComment : dailyNoteComment.getReplies()){
            childs.add(new DailyNoteChildCommentResponseDto(dailyNoteChildComment));
        }
        this.createdTime = dailyNoteComment.getCreatedTime();
    }
}
