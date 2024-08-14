package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.kid.KidBasicInfoResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.member.rtype;

@Getter
@NoArgsConstructor
public class DailyNoteListItemResponseDto {
    private Long id;

    private String banName;
    private String kidName;
    private rtype writerRole;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime sendTime;

    public DailyNoteListItemResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.banName = dailyNote.getKid().getBan().getName();
        this.kidName = dailyNote.getKid().getName();
        this.writerRole = dailyNote.getWriter().getRole();
        this.sendTime = dailyNote.getSendTime();
    }
}
