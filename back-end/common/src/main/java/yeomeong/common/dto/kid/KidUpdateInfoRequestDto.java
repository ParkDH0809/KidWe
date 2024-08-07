package yeomeong.common.dto.kid;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import yeomeong.common.entity.member.gtype;

@Getter
public class KidUpdateInfoRequestDto {

    long id;
    String name;
    LocalDate birthday;
    gtype gender;
    List<String> allergies;
    String picture;
    Long banId;
    Long kindergartenId;

    public boolean hasBanId() {
        return this.banId != null;
    }

    public boolean hasKindergartenId() {
        return this.kindergartenId != null;
    }

}
