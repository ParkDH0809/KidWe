package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.kid.KidDetailInfoResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
@NoArgsConstructor
public class DailyNoteResponseDto {
    private Long id;

    private Post post;

    private KidDetailInfoResponseDto kid;
    private MemberProfileResponseDto writer;
    private List<DailyNoteParentCommentResponseDto> comments;

    private LocalDateTime sendTime;

    public DailyNoteResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();

        this.post = dailyNote.getPost();

        this.kid = KidDetailInfoResponseDto.toKidDetailInfoDto(dailyNote.getKid());
        this.writer = MemberProfileResponseDto.toMemberProfileDto(dailyNote.getWriter());
        this.comments = new ArrayList<>();
        for(DailyNoteComment comment : dailyNote.getComments()){
            if(comment.getParentComment()==null){
                comments.add(new DailyNoteParentCommentResponseDto(comment));
            }
        }

        this.sendTime = dailyNote.getSendTime();
    }
}
