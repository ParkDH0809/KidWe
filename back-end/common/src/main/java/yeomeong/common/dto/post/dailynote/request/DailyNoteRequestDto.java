package yeomeong.common.dto.post.dailynote.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;

@Getter
public class DailyNoteRequestDto {
    private Post post;

    private Long kidId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
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