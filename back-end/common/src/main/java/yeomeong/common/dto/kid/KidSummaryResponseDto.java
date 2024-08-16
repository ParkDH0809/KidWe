package yeomeong.common.dto.kid;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Kid;

@Getter
@NoArgsConstructor
public class KidSummaryResponseDto {
    private Long id;
    private String name;
    private String picture;

    public KidSummaryResponseDto(Kid kid){
        this.id = kid.getId();
        this.name = kid.getName();
        this.picture = kid.getPicture();
    }
}
