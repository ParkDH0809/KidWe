package yeomeong.common.dto.ban;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.kindergarten.Ban;

@Builder
@Getter
public class BanBasicInfoResponseDto {

    private long id;
    private String name;

    public static BanBasicInfoResponseDto toBanBasicInfoDto(Ban ban) {
        return BanBasicInfoResponseDto.builder().id(ban.getId()).name(ban.getName()).build();
    }
}
