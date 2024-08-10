package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;
    private String stringSendTime;

    public DailyNoteListItemResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.kid = KidBasicInfoResponseDto.toKidBasicInfoDto(dailyNote.getKid());
        this.writer = MemberProfileResponseDto.toMemberProfileDto(dailyNote.getWriter());
        this.sendTime = dailyNote.getSendTime();
        this.stringSendTime = sendTime.toString();
    }
}
