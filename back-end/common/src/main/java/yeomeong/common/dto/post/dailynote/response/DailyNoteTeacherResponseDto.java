package yeomeong.common.dto.post.dailynote.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.member.TeacherSummaryResponseDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;
import yeomeong.common.entity.post.comment.DailyNoteComment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DailyNoteTeacherResponseDto {
    private Long id;

    private Post post;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    private TeacherSummaryResponseDto writer;
    private List<DailyNoteParentCommentResponseDto> comments;

    @Builder
    public DailyNoteTeacherResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.post = dailyNote.getPost();
        this.sendTime = dailyNote.getSendTime();

        this.writer = new TeacherSummaryResponseDto(dailyNote.getWriter());
        this.comments = new ArrayList<>();
        for(DailyNoteComment comment : dailyNote.getComments()){
            if(comment.getParentComment()==null){
                comments.add(new DailyNoteParentCommentResponseDto(comment));
            }
        }
    }
}
