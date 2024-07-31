package yeomeong.common.dto.member;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.member.KidMember;
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
    private Ban ban;
    private Kindergarten kindergarten;
    private List<KidMember> kidMembers;

    public static MemberProfileResponseDto toMemberProfileDto(Member member) {
        return MemberProfileResponseDto
            .builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .tel(member.getTel())
            .role(member.getRole())
            .ban(member.getBan())
            .kindergarten(member.getKindergarten())
            .build();
    }

}
