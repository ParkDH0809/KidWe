package yeomeong.common.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Member;

@Getter
@NoArgsConstructor
public class TeacherSummaryResponseDto {
    private Long id;
    private String name;
    private String picture;

    public TeacherSummaryResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.picture = "";
    }
}
