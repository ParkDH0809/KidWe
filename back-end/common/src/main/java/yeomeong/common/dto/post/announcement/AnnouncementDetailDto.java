package yeomeong.common.dto.post.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.post.Post;
import yeomeong.common.entity.post.Vote;
import yeomeong.common.entity.post.comment.AnnouncementComment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDetailDto {

    private Long writerId;

    private Post post;

    private Vote vote;

    private List<AnnouncementComment> comment;

}
