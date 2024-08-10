package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.kid.KidDetailInfoResponseDto;
import yeomeong.common.dto.kid.KidSummaryResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteGuardianResponseDto {
    private Long id;

    private Post post;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    private KidSummaryResponseDto kid;
    private List<DailyNoteParentCommentResponseDto> comments;

    @Builder
    public DailyNoteGuardianResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.post = dailyNote.getPost();

        this.kid = new KidSummaryResponseDto(dailyNote.getKid());
        this.comments = new ArrayList<>();
        for(DailyNoteComment comment : dailyNote.getComments()){
            if(comment.getParentComment()==null){
                comments.add(new DailyNoteParentCommentResponseDto(comment));
            }
        }

        this.sendTime = dailyNote.getSendTime();
    }
}
