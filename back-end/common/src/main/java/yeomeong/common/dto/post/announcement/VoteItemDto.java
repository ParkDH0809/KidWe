package yeomeong.common.dto.post.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteItemDto {

    private Long voteItemId;
    private String itemName;
    private Integer value;
}
