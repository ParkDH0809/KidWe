package yeomeong.common.dto.post.dailynote.request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.Data;
import lombok.Getter;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Getter
public class DailyNoteCreateRequestDto {
    private Post post;

    private Long kidId;
    private Long writerId;

    private LocalDateTime sendTime;

    public DailyNote toEntity(Kid kid,
                                Member writer){
        return DailyNote.builder()
            .post(this.post)
            .kid(kid)
            .writer(writer)
            .sendTime(this.sendTime)
            .build();
    }
}