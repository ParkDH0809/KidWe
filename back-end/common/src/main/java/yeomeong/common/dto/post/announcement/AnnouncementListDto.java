package yeomeong.common.dto.post.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.rtype;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnnouncementListDto {

    private Long announcementId;
    private String title;
    private String memberName;
    private String memberBan;
    private rtype writerRole;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.M.d HH:mm")
    private LocalDateTime createdDate;
    private int commentCnt;


    public AnnouncementListDto(Long announcementId,String title, String memberName, String memberBan, rtype writerRole, LocalDateTime createdDateTime, int commentCnt) {
        this.announcementId = announcementId;
        this.title = title;
        this.memberName = memberName;
        this.writerRole = writerRole;
        this.memberBan = memberBan;
        this.createdDate = createdDateTime;
        this.commentCnt = commentCnt;
    }

    public AnnouncementListDto(Long announcementId,String title, String memberName, rtype writerRole, LocalDateTime createdDateTime, int commentCnt) {
        this.announcementId = announcementId;
        this.title = title;
        this.memberName = memberName;
        this.writerRole = writerRole;
        this.createdDate = createdDateTime;
        this.commentCnt = commentCnt;
    }

}



