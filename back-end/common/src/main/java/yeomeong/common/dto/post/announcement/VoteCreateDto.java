package yeomeong.common.dto.post.announcement;


import lombok.Data;
import yeomeong.common.entity.post.VoteItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class VoteCreateDto {

    private String title;
    private LocalDate voteStartDate;
    private LocalDate voteEndDate;
    private List<VoteItemRequestDto> items;

}
