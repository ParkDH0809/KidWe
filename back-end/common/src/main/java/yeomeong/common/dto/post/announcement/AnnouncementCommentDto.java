package yeomeong.common.dto.post.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import yeomeong.common.entity.member.rtype;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AnnouncementCommentDto {

    private Long commentId;
    private String memberProfile;
    private rtype memberRole;

    private String memberName;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M.d HH:mm")
    private LocalDateTime dateTimeWritten;

    private boolean canDelete;
    private List<AnnouncementCommentChildDto> children = new ArrayList<>();


    public AnnouncementCommentDto(Long id,String memberProfile, rtype memberRole, String memberName, String content, LocalDateTime dateTimeWritten, boolean canDelete) {
        this.commentId = id;
        this.memberProfile = memberProfile;
        this.memberRole = memberRole;
        this.memberName = memberName;
        this.content = content;
        this.dateTimeWritten = dateTimeWritten;
        this.canDelete = canDelete;
    }
}
