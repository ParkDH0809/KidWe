package yeomeong.common.dto.post.announcement;


import lombok.Data;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.post.Post;

@Data
@NoArgsConstructor
public class AnnouncementCreateDto {

    private Post post;

}
