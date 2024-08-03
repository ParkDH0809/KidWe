package yeomeong.common.dto.post.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import yeomeong.common.entity.post.VoteItem;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class VoteResultDto {

    private Long voteItemId;
    private String itemName;
    private int value;
}
