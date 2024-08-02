package yeomeong.common.dto.member;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;

@Builder
@Getter
public class MemberProfileResponseDto {

    private long id;
    private String name;
    private String email;
    private String tel;
    private rtype role;

    public static MemberProfileResponseDto toMemberProfileDto(Member member) {
        return MemberProfileResponseDto
            .builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .tel(member.getTel())
            .role(member.getRole())
            .build();
    }

}
