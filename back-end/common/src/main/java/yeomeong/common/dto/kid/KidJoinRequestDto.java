package yeomeong.common.dto.kid;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.gtype;

@Getter
@ToString
public class KidJoinRequestDto {

    String name;
    LocalDate birthday;
    gtype gender;
    List<String> allergies;
    String picture;
    Long kindergartenId;
    Long banId;

    public static Kid toKidEntity(KidJoinRequestDto kidJoinRequestDto,
        Kindergarten kindergarten, Ban ban) {
        return Kid.builder()
            .name(kidJoinRequestDto.getName())
            .birthday(kidJoinRequestDto.getBirthday())
            .allergies(listToString(kidJoinRequestDto.getAllergies()))
            .picture(kidJoinRequestDto.getPicture())
            .gender(kidJoinRequestDto.getGender())
            .ban(ban)
            .kindergarten(kindergarten)
            .build();
    }

    private static String listToString(List<String> list) {
        return String.join(",", list);
    }

}
