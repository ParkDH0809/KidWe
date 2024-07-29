package yeomeong.common.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.entity.jpa.member.rtype;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDto {

    private String name;
    private String tel;
    private String email;
    private String password;
    private String role;

    public static Member toMemberEntity(JoinRequestDto joinRequestDto) {
        return Member.builder()
                .name(joinRequestDto.getName())
                .tel(joinRequestDto.getTel())
                .email(joinRequestDto.getEmail())
                .password(joinRequestDto.getPassword())
                .role(rtype.valueOf(joinRequestDto.getRole()))
                .build();
    }

}