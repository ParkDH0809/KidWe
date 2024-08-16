package yeomeong.common.dto.post.announcement;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.post.Post;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementCreateDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.M.d HH:mm")
    private LocalDateTime createdDateTime;

    private String title;

    private String content;


    public AnnouncementCreateDto(Post post){
        this.createdDateTime = post.getCreatedDateTime();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
