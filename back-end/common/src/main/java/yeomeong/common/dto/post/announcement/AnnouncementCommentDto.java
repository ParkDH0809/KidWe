package yeomeong.common.dto.post.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnnouncementCommentDto {

    private Long commentId;
    @Nullable
    private Long parentCommentId;
    private String memberName;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M.d HH:mm")
    private LocalDateTime dateTimeWritten;


}
