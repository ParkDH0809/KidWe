package yeomeong.common.dto.post.dailynote.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.lang.Nullable;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;

@Getter
public class DailyNoteRequestDto {
    private Long kidId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;
    private String content;
    private Boolean isStored;

    public DailyNote toEntity(Kid kid,
                                Member writer){
        return DailyNote.builder()
            .kid(kid)
            .writer(writer)
            .content(this.content)
            .sendTime(this.sendTime == null ? LocalDateTime.now() : this.sendTime)
            .build();
    }
}