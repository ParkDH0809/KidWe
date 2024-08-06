package yeomeong.common.dto.member;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.dto.ban.BanBasicInfoResponseDto;
import yeomeong.common.entity.member.Member;

@Getter
@Builder
public class TeacherDetailInfoResponseDto {

    long id;
    String name;
    String tel;
    BanBasicInfoResponseDto ban;

    public static TeacherDetailInfoResponseDto toTeacherDetailInfoDto(Member member) {
        return TeacherDetailInfoResponseDto.builder()
            .id(member.getId())
            .name(member.getName())
            .tel(member.getTel())
            .ban(BanBasicInfoResponseDto.toBanBasicInfoDto(member.getBan()))
            .build();
    }

}
