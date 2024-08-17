package yeomeong.common.dto.post.dailynote.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.member.TeacherSummaryResponseDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.DailyNoteImage;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteResponseDto {
    private Long id;

    private String name;
    private String picture;
    private rtype role;

    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    private List<String> images;

    private Boolean canDelete;

    private Long commentCount;
    private List<DailyNoteParentCommentResponseDto> comments;

    // 작성자가 학부모인 경우
    @Builder
    public DailyNoteResponseDto(Long memberId, DailyNote dailyNote, Kid kid) {
        this.id = dailyNote.getId();

        this.name = kid.getName();
        this.picture = kid.getPicture();
        this.role = rtype.ROLE_GUARDIAN;

        this.content = dailyNote.getContent();
        this.sendTime = dailyNote.getSendTime();

        this.images = new ArrayList<>();
        for(DailyNoteImage image : dailyNote.getImages()) {
            images.add(image.getImageUrl());
        }

        canDelete = memberId == dailyNote.getWriter().getId() ? true : false;
        this.comments = new ArrayList<>();
        for(DailyNoteComment comment : dailyNote.getComments()){
            if(comment.getParentComment()==null){
                comments.add(new DailyNoteParentCommentResponseDto(memberId, comment));
            }
        }
        Collections.sort(comments, (a, b) -> {
            return a.getCreatedTime().isAfter(b.getCreatedTime()) ? 1 : -1;
        });
        commentCount = comments.stream()
                .filter(comment -> !comment.getIsDeleted())
                .count();

    }

    @Builder
    public DailyNoteResponseDto(Long memberId, DailyNote dailyNote) {
        this.id = dailyNote.getId();

        Member writer = dailyNote.getWriter();
        this.name = writer.getName();
        this.picture = writer.getPicture();
        this.role = rtype.ROLE_TEACHER;

        this.content = dailyNote.getContent();
        this.sendTime = dailyNote.getSendTime();

        images = new ArrayList<>();
        for(DailyNoteImage image : dailyNote.getImages()){
            images.add(image.getImageUrl());
        }

        canDelete = memberId == dailyNote.getWriter().getId() ? true : false;

        this.comments = new ArrayList<>();
        for(DailyNoteComment comment : dailyNote.getComments()){
            if(comment.getParentComment()==null){
                comments.add(new DailyNoteParentCommentResponseDto(memberId, comment));
            }
        }
        Collections.sort(comments, (a, b) -> {
            return a.getCreatedTime().isAfter(b.getCreatedTime()) ? 1 : -1;
        });
        commentCount = comments.stream()
            .filter(comment -> !comment.getIsDeleted())
            .count();
    }
}
