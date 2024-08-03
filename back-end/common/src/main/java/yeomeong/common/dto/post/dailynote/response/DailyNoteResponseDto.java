package yeomeong.common.dto.post.dailynote.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.dto.kid.KidDetailInfoDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Post;

@Getter
@NoArgsConstructor
public class DailyNoteResponseDto {
    private Long id;

    private Post post;

    private KidDetailInfoDto kid;
    private MemberProfileResponseDto writer;

    private LocalDateTime sendTime;

    public DailyNoteResponseDto(DailyNote dailyNote) {
        this.id = dailyNote.getId();
        this.post = dailyNote.getPost();
        this.kid = KidDetailInfoDto.toKidDetailInfoDto(dailyNote.getKid());
        this.writer = MemberProfileResponseDto.toMemberProfileDto(dailyNote.getWriter());
        this.sendTime = dailyNote.getSendTime();
    }
}
