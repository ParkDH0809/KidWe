package yeomeong.common.dto.post.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import yeomeong.common.entity.member.rtype;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AnnouncementCommentDto {

    private Long id;
    private String picture;
    private rtype role;
    private String name;
    private String banName;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M.d HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;

    private boolean canDelete;
    private List<AnnouncementCommentChildDto> childs = new ArrayList<>();


    public AnnouncementCommentDto(Long id, String picture, rtype role, String name, String banName,String content, LocalDateTime createdTime, boolean canDelete) {
        this.id = id;
        this.picture = picture;
        this.role = role;
        this.name = name;
        this.banName = banName;
        this.content = content;
        this.createdTime = createdTime;
        this.canDelete = canDelete;
    }
}
