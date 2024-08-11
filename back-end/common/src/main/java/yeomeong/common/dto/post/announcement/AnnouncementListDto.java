package yeomeong.common.dto.post.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnnouncementListDto {

    private Long announcementId;
    private String title;
    private String memberName;
    private String memberBan;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.M.d HH:mm")
    private LocalDateTime createdDate;
    private int commentCnt;


    public AnnouncementListDto(Long announcementId,String title, String memberName, String memberBan, LocalDateTime createdDateTime, int commentCnt) {
        this.announcementId = announcementId;
        this.title = title;
        this.memberName = memberName;
        this.memberBan = memberBan;
        this.createdDate = createdDateTime;
        this.commentCnt = commentCnt;
    }

    public AnnouncementListDto(Long announcementId,String title, String memberName, LocalDateTime createdDateTime, int commentCnt) {
        this.announcementId = announcementId;
        this.title = title;
        this.memberName = memberName;
        this.createdDate = createdDateTime;
        this.commentCnt = commentCnt;
    }

}



