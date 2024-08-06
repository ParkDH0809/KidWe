package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.dto.kid.KidBasicInfoDto;
import yeomeong.common.dto.kid.KidDetailInfoDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;

@Getter
@NoArgsConstructor
public class DailyNoteListItemResponseDto {
    private Long id;
    private KidBasicInfoDto kid;
    private MemberProfileResponseDto writer;
    private LocalDateTime sendTime;

    public DailyNoteListItemResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.kid = KidBasicInfoDto.toKidBasicInfoDto(dailyNote.getKid());
        this.writer = MemberProfileResponseDto.toMemberProfileDto(dailyNote.getWriter());
        this.sendTime = dailyNote.getSendTime();
    }
}
