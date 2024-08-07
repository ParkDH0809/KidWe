package yeomeong.common.document;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import yeomeong.common.dto.Kid;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor

@Document(collection = "memo")
public class Memo {

    @Id
    private String id;
    @Field("teacher_id")
    @Indexed
    private Long teacherId;
    @Field("created_time")
    private LocalDateTime createdTime;

    @Field("updated_time")
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime updatedTime;
    private String date;
    @Field("is_deleted")
    private Boolean isDeleted;

    private String lesson;
    private List<Kid> kids;
    private List<Tag> tags;
    private String content;

    @Transient
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Transient
    private final ZoneId zoneKorea = ZoneId.of("Asia/Seoul");

    @Builder
    public Memo(Long teacherId,
        String updatedTime,
        String lesson,
        List<Kid> kids,
        List<Tag> tags,
        String content) {
        this.teacherId = teacherId;
        this.createdTime = LocalDateTime.now(zoneKorea);
        if (updatedTime != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(updatedTime, formatter);
            this.updatedTime = localDateTime.atZone(zoneKorea).toLocalDateTime();
        } else {
            this.updatedTime = this.createdTime;
        }
        this.date = this.updatedTime.toString().split("T")[0];
        this.isDeleted = false;
        this.lesson = lesson;
        this.kids = kids;
        this.tags = tags;
        this.content = content;
    }

    public void setNewUpdatedTime(LocalDateTime newUpdatedTime) {
        this.updatedTime = updatedTime == null? this.updatedTime: this.createdTime;
        this.date = this.updatedTime.toString().split("T")[0];
    }

    public void setNewLesson(String newLesson) {
        this.lesson = newLesson;
    }

    public void setNewKids(List<Kid> kids){
        this.kids = kids;
    }

    public void setNewTags(List<Tag> tags){
        this.tags = tags;
    }

    public void setNewContent(String content){
        this.content = content;
    }

    public void delete(){
        this.isDeleted = true;
    }
}
