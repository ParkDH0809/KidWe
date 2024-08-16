package yeomeong.common.dto.approval;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.atype;
import yeomeong.common.entity.member.gtype;

@Getter
@ToString
public class KidJoinKindergartenRequestDto {

    Long memberId;
    String kidName;
    LocalDate birthday;
    gtype gender;
    List<String> allergies;
    Long kindergartenId;
    Long banId;

    public static Kid toKidEntity(KidJoinKindergartenRequestDto kidJoinKindergartenRequestDto, String picture) {
        return Kid.builder()
            .name(kidJoinKindergartenRequestDto.getKidName())
            .birthday(kidJoinKindergartenRequestDto.getBirthday())
            .allergies(listToString(kidJoinKindergartenRequestDto.getAllergies()))
            .picture(picture)
            .gender(kidJoinKindergartenRequestDto.getGender())
            .kidStatus(atype.PENDING)
            .isDeleted(false)
            .build();
    }

    private static String listToString(List<String> list) {
        return String.join(",", list);
    }

}
