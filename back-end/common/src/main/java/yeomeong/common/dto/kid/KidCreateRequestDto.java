package yeomeong.common.dto.kid;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.gtype;

@Getter
@ToString
public class KidCreateRequestDto {

    String name;
    Date birthday;
    gtype gender;
    List<String> allergies;
    String picture;
    Long kindergartenId;
    Long banId;

    public static Kid toKidEntity(KidCreateRequestDto kidCreateRequestDto,
        Kindergarten kindergarten, Ban ban) {
        return Kid.builder()
            .name(kidCreateRequestDto.getName())
            .birthday(kidCreateRequestDto.getBirthday())
            .allergies(listToString(kidCreateRequestDto.getAllergies()))
            .picture(kidCreateRequestDto.getPicture())
            .gender(kidCreateRequestDto.getGender())
            .ban(ban)
            .kindergarten(kindergarten)
            .build();
    }

    private static String listToString(List<String> list) {
        return String.join(",", list);
    }

}
