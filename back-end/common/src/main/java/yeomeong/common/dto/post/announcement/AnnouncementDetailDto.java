package yeomeong.common.dto.post.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.jpa.post.Post;
import yeomeong.common.entity.jpa.post.comment.AnnouncementComment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDetailDto {

    private Long writerId;

    private Post post;

    private List<AnnouncementComment> comment;

}
