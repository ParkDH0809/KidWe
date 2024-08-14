package yeomeong.common.dto.kid;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Kid;

@Builder
@Getter
public class KidBasicInfoResponseDto {

    long id;
    String name;
    String image;

    public static KidBasicInfoResponseDto toKidBasicInfoDto(Kid kid) {
        return KidBasicInfoResponseDto.builder()
                .id(kid.getId())
                .name(kid.getName())
                .image(kid.getPicture())
                .build();
    }

}
