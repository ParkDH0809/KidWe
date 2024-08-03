package yeomeong.common.dto.ban;

import lombok.Getter;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;

@Getter
public class BanCreateRequestDto {

    Long kindergartenId;
    String name;

    public static Ban toBanEntity(Kindergarten kindergarten, BanCreateRequestDto banCreateRequestDto) {
        return Ban.builder()
            .kindergarten(kindergarten)
            .name(banCreateRequestDto.getName())
            .build();
    }

}
