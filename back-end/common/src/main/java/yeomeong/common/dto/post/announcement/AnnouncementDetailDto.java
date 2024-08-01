package yeomeong.common.dto.post.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.post.Post;
import yeomeong.common.entity.post.Vote;
import yeomeong.common.entity.post.VoteItem;
import yeomeong.common.entity.post.comment.AnnouncementComment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDetailDto {

    private String banName;

    private Post post;

    private List<VoteItem> voteItems;

    private List<AnnouncementCommentDto> comment;

}
