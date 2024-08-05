package yeomeong.common.dto.kid;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.gtype;

@Builder
@Getter
public class KidDetailInfoDto {

    private long id;
    private String name;
    private Date birthday;
    private Date startAttendanceDate;
    private gtype gender;
    private double tall;
    private double weight;
    private List<String> allergies;
    private String picture;
    private boolean isTake;
    private long banId;
    private String banName;
    private long stopId;
    private long busId;

    public static KidDetailInfoDto toKidDetailInfoDto(Kid kid) {
        return KidDetailInfoDto.builder()
            .id(kid.getId())
            .name(kid.getName())
            .birthday(kid.getBirthday())
            .startAttendanceDate(kid.getStartAttendanceDate())
            .gender(kid.getGender())
            .allergies(stringToList(kid.getAllergies()))
            .picture(kid.getPicture())
            .isTake(kid.isTake())
            .banId(kid.getBan().getId())
            .banName(kid.getBan().getName())
            .build();
    }

    private static List<String> stringToList(String str) {
        return Arrays.stream(str.split(",")).toList();
    }

}
