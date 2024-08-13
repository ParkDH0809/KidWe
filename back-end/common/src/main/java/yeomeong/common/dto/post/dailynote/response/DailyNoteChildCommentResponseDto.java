package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.kid.KidSummaryResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.TeacherSummaryResponseDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteChildCommentResponseDto {
    private Long id;

    private String name;
    private String picture;
    private rtype role;

    private Boolean canDelete;
    private Boolean isDeleted;
    @JsonIgnore
    private final static String deletedMessage = "삭제된 댓글입니다";
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;

    public DailyNoteChildCommentResponseDto(Long memberId, DailyNoteComment dailyNoteComment) {
        this.id = dailyNoteComment.getId();

        if(dailyNoteComment.getMember().getRole() == rtype.ROLE_GUARDIAN) {
            this.role = rtype.ROLE_GUARDIAN;
            Kid kid = dailyNoteComment.getDailyNote().getKid();
            this.name = kid.getName();
            this.picture = kid.getPicture()==null?"": kid.getPicture();
        }
        else{
            this.role = rtype.ROLE_TEACHER;
            Member teacher = dailyNoteComment.getMember();
            this.name = teacher.getName();
            this.picture = teacher.getPicture()==null?"": teacher.getPicture();
        }

        this.canDelete = memberId == dailyNoteComment.getMember().getId() ? true : false;

        if(dailyNoteComment.getIsDeleted()) {
            this.isDeleted = true;
            this.content = deletedMessage;
        }
        else{
            this.isDeleted = false;
            this.content = dailyNoteComment.getContent();
        }

        this.createdTime = dailyNoteComment.getCreatedTime();
    }
}
