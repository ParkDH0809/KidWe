package yeomeong.common.dto.post.dailynote.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZoneId;
import lombok.Getter;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;

@Getter
public class DailyNoteRequestDto {
    private Long kidId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;
    private String content;

    public DailyNote toEntity(Kid kid,
                                Member writer){

        LocalDateTime sendTimeInKorea = this.sendTime != null
            ? this.sendTime
            : LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        return DailyNote.builder()
            .kid(kid)
            .writer(writer)
            .content(this.content)
            .sendTime(this.sendTime == null ? sendTimeInKorea : this.sendTime)
            .build();
    }
}