package yeomeong.common.dto.post.announcement;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.Post;
import yeomeong.common.entity.post.Vote;
import yeomeong.common.entity.post.VoteItem;
import yeomeong.common.entity.post.comment.AnnouncementComment;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDetailDto {

    private String memberProfile;

    private rtype rtype;

    private String banName;

    private Post post;

    private List<AnnouncementImageDto> announcementImages;

    private Long voteId;

    private List<VoteItemDto> voteItems;

    private int commentCnt;

    private List<AnnouncementCommentDto> comment = new ArrayList<>();

    private boolean canDelete;

}
