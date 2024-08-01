package yeomeong.common.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequestDto {

    private String name;
    private String tel;
    private String email;
    private String password;
    private String role;

    public static Member toMemberEntity(MemberSaveRequestDto memberSaveRequestDto) {
        return Member.builder()
            .name(memberSaveRequestDto.getName())
            .tel(memberSaveRequestDto.getTel())
            .email(memberSaveRequestDto.getEmail())
            .password(memberSaveRequestDto.getPassword())
            .role(rtype.valueOf(memberSaveRequestDto.getRole()))
            .build();
    }

}
