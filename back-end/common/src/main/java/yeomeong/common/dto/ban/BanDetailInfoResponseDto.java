package yeomeong.common.dto.ban;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import yeomeong.common.dto.kid.KidBasicInfoResponseDto;
import yeomeong.common.dto.member.TeacherBasicInfoResponseDto;
import yeomeong.common.entity.kindergarten.Ban;

@Getter
@Builder
public class BanDetailInfoResponseDto {

    private long id;
    private String name;
    @Builder.Default
    private List<KidBasicInfoResponseDto> kids = new ArrayList<>();
    private int kidCount;
    private List<TeacherBasicInfoResponseDto> teachers;
    private int teacherCount;

    public static BanDetailInfoResponseDto toBanDetailInfoDto(Ban ban) {
        return BanDetailInfoResponseDto.builder()
            .id(ban.getId())
            .name(ban.getName())
            .kids(ban.getKids().stream()
                .map(KidBasicInfoResponseDto::toKidBasicInfoDto)
                .collect(Collectors.toList()))
            .build();
    }

    public void initializeDefaults(List<TeacherBasicInfoResponseDto> teacherInfos) {
        this.teachers = teacherInfos;
        this.teacherCount = teacherInfos.size();
        this.kidCount = kids.size();
    }

}
