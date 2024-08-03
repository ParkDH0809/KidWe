package yeomeong.common.dto.ban;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.kindergarten.Ban;

@Builder
@Getter
public class BanBasicInfoDto {

    private long id;
    private String name;

    public static BanBasicInfoDto toBanBasicInfoDto(Ban ban) {
        return BanBasicInfoDto.builder().id(ban.getId()).name(ban.getName()).build();
    }
}
