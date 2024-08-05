package yeomeong.common.dto.member;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Member;

@Getter
@Builder
public class TeacherBasicInfoDto {

    long id;
    String name;
    String tel;

    public static TeacherBasicInfoDto toTeacherInfoDto(Member member) {
        return TeacherBasicInfoDto.builder()
            .id(member.getId())
            .name(member.getName())
            .tel(member.getTel())
            .build();
    }

}