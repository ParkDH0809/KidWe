package yeomeong.common.dto.kid;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Kid;

@Builder
@Getter
public class KidBasicInfoDto {

    long id;
    String name;

    public static KidBasicInfoDto toKidBasicInfoDto(Kid kid) {
        return KidBasicInfoDto.builder().id(kid.getId()).name(kid.getName()).build();
    }

}
