package yeomeong.common.dto.post.announcement;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnnouncementListDto {

    private String title;
    private String memberName;
    private String memberBan;
    private LocalDateTime createdDate;
    private int commentCnt;


    public AnnouncementListDto(String title, String memberName, String memberBan, LocalDateTime createdDateTime, int commentCnt) {
        this.title = title;
        this.memberName = memberName;
        this.memberBan = memberBan;
        this.createdDate = createdDateTime;
        this.commentCnt = commentCnt;
    }

    public AnnouncementListDto(String title, String memberName, LocalDateTime createdDateTime, int commentCnt) {
        this.title = title;
        this.memberName = memberName;
        this.createdDate = createdDateTime;
        this.commentCnt = commentCnt;
    }

}



