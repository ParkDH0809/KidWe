package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.kid.KidBasicInfoResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.entity.post.DailyNote;

@Getter
@NoArgsConstructor
public class DailyNoteListItemResponseDto {
    private Long id;
    private KidBasicInfoResponseDto kid;
    private MemberProfileResponseDto writer;
    private String sendTime;

    public DailyNoteListItemResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.kid = KidBasicInfoResponseDto.toKidBasicInfoDto(dailyNote.getKid());
        this.writer = MemberProfileResponseDto.toMemberProfileDto(dailyNote.getWriter());
        this.sendTime = dailyNote.getSendTime().toString;
    }
}
