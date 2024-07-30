package yeomeong.common.dto.post.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class VoteResultDto {

    private Map<String, Integer> items;
}
