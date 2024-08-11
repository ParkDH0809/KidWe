package yeomeong.common.dto.post.dailynote.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.member.TeacherSummaryResponseDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.DailyNoteImage;
import yeomeong.common.entity.post.Post;
import yeomeong.common.entity.post.comment.DailyNoteComment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
public class DailyNoteTeacherResponseDto {
    private Long id;

    private Post post;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    private TeacherSummaryResponseDto writer;
    private Long commentCount;
    private List<DailyNoteParentCommentResponseDto> comments;
    
    private List<String> tumbnails;
    private List<String> images;

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
        Collections.sort(comments, (a, b) -> {
            return a.getCreatedTime().isEqual(b.getCreatedTime()) ? 1 : -1;
        });
        commentCount = comments.stream()
                .filter(comment -> !comment.getIsDeleted())
                .count();

        images = new ArrayList<>();
        for(DailyNoteImage image : dailyNote.getImages()){
            images.add(image.getImageUrl());
        }
    }
}
