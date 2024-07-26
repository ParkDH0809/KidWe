package yeomeong.common.dto.post.announcement;


import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class VoteCreateDto {

    private String title;
    private LocalDate voteStartDate;
    private LocalDate voteEndDate;
    private Map<String, Integer> candidate;

}
