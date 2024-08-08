package yeomeong.common.dto.kindergarten;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "KindergartenSearchDto")
public class KindergartenSearchRequestDto {

    private String sido;
    private String sigungu;
    private String search;

}
