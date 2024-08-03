package yeomeong.common.dto.post.announcement;

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
    private LocalDateTime dateTimeWritten;


}
