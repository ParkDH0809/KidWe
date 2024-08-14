package yeomeong.common.dto.post.announcement;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import yeomeong.common.entity.member.rtype;

import java.time.LocalDateTime;

@Data
public class AnnouncementCommentChildDto {

    private Long commentId;
    private String memberProfile;
    private rtype memberRole;

    private String memberName;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M.d HH:mm")
    private LocalDateTime dateTimeWritten;

    private boolean canDelete;

    public AnnouncementCommentChildDto(Long commentId, String memberProfile, rtype memberRole, String memberName, String content, LocalDateTime dateTimeWritten, boolean canDelete) {
        this.commentId = commentId;
        this.memberProfile = memberProfile;
        this.memberRole = memberRole;
        this.memberName = memberName;
        this.content = content;
        this.dateTimeWritten = dateTimeWritten;
        this.canDelete = canDelete;
    }
}
