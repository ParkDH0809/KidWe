package yeomeong.common.dto.ban;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import yeomeong.common.dto.kid.KidBasicInfoDto;
import yeomeong.common.dto.member.TeacherBasicInfoDto;
import yeomeong.common.entity.kindergarten.Ban;

@Getter
@Builder
public class BanDetailInfoDto {

    private long id;
    private String name;
    @Builder.Default
    private List<KidBasicInfoDto> kids = new ArrayList<>();
    private int kidCount;
    private List<TeacherBasicInfoDto> teachers;
    private int teacherCount;

    public static BanDetailInfoDto toBanDetailInfoDto(Ban ban) {
        return BanDetailInfoDto.builder()
            .id(ban.getId())
            .name(ban.getName())
            .kids(ban.getKids().stream()
                .map(KidBasicInfoDto::toKidBasicInfoDto)
                .collect(Collectors.toList()))
            .build();
    }

    public void initializeDefaults(List<TeacherBasicInfoDto> teacherInfos) {
        this.teachers = teacherInfos;
        this.teacherCount = teacherInfos.size();
        this.kidCount = kids.size();
    }

}
