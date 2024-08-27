package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
public class DailyNoteParentCommentResponseDto {
    private Long id;

    private rtype role;
    private String name;
    private String picture;
    private String banName;

    private Boolean canDelete;
    private Boolean isDeleted;
    @JsonIgnore
    private final static String deletedMessage = "삭제된 댓글입니다";

    private String content;
    private List<DailyNoteChildCommentResponseDto> childs;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;

    public DailyNoteParentCommentResponseDto(Long memberId, DailyNoteComment dailyNoteComment) {
        this.id = dailyNoteComment.getId();
        if(dailyNoteComment.getMember().getRole() == rtype.ROLE_GUARDIAN) {
            this.role = rtype.ROLE_GUARDIAN;
            Kid kid = dailyNoteComment.getDailyNote().getKid();
            this.banName = kid.getBan().getName();
            this.name = kid.getName();
            this.picture = kid.getPicture()==null?"": kid.getPicture();
        }
        else if(dailyNoteComment.getMember().getRole() == rtype.ROLE_TEACHER) {
            this.role = rtype.ROLE_TEACHER;
            Member teacher = dailyNoteComment.getMember();
            this.banName = teacher.getBan().getName();
            this.name = teacher.getName();
            this.picture = teacher.getPicture()==null?"": teacher.getPicture();
        }
        // 원장님은 반 이름이 없어요!!!
        else {
            this.role = rtype.ROLE_DIRECTOR;
            Member director = dailyNoteComment.getMember();
            this.banName = null;
            this.name = director.getName();
            this.picture = director.getPicture()==null?"": director.getPicture();
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

        childs = new ArrayList<>();
        for(DailyNoteComment dailyNoteChildComment : dailyNoteComment.getReplies()){
            childs.add(new DailyNoteChildCommentResponseDto(memberId, dailyNoteChildComment));
        }
        Collections.sort(childs, (a, b) -> {
            return a.getCreatedTime().isAfter(b.getCreatedTime()) ? 1 : -1;
        });
        this.createdTime = dailyNoteComment.getCreatedTime();
    }
}
