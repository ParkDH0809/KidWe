package yeomeong.common.dto.post.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnnouncementCommentDto {

    private Long commentId;
    private String memberName;
    private String content;
    private LocalDateTime dateTimeWritten;


}
