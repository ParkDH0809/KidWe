package yeomeong.common.dto.post.dailynote.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;

@Getter
@NoArgsConstructor
public class DailyNoteUpdateRequestDto {
    private Long id;

    private Post post;

    private Long kidId;
    private Long writerId;

    private LocalDateTime sendTime;

    public DailyNote toEntity(Kid kid,
        Member writer){
        return DailyNote.builder()
            .id(id)
            .post(this.post)
            .kid(kid)
            .writer(writer)
            .sendTime(this.sendTime)
            .isDeleted(false)
            .build();
    }
}
