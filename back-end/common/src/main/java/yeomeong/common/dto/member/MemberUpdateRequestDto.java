package yeomeong.common.dto.member;

import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    Long id;
    String name;
    String tel;
    String picture;
    String password;

}
