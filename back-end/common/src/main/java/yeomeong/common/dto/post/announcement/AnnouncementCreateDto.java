package yeomeong.common.dto.post.announcement;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.jpa.post.Post;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementCreateDto {

    private Post post;

}
