package yeomeong.common.dto.member;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.dto.ban.BanBasicInfoDto;
import yeomeong.common.entity.member.Member;

@Getter
@Builder
public class TeacherDetailInfoDto {

    long id;
    String name;
    String tel;
    BanBasicInfoDto ban;

    public static TeacherDetailInfoDto toTeacherDetailInfoDto(Member member) {
        return TeacherDetailInfoDto.builder()
            .id(member.getId())
            .name(member.getName())
            .tel(member.getTel())
            .ban(BanBasicInfoDto.toBanBasicInfoDto(member.getBan()))
            .build();
    }

}
