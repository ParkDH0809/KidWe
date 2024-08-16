package yeomeong.common.dto.post.dailynote.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class DailyNoteUpdateRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;
    private String content;
}
