package yeomeong.common.dto.post.announcement;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import yeomeong.common.entity.member.rtype;

import java.time.LocalDateTime;

@Data
public class AnnouncementCommentChildDto {

    private Long id;
    private String picture;
    private rtype role;
    private String name;
    private String banName;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M.d HH:mm")
    private LocalDateTime createdTime;

    private boolean canDelete;

    public AnnouncementCommentChildDto(Long id, String picture, rtype role, String writerName,String banName, String content, LocalDateTime createdTime, boolean canDelete) {
        this.id = id;
        this.picture = picture;
        this.role = role;
        this.name = writerName;
        this.banName = banName;
        this.content = content;
        this.createdTime = createdTime;
        this.canDelete = canDelete;
    }
}
