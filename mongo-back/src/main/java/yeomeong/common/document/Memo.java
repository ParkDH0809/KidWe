package yeomeong.common.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import yeomeong.common.dto.MemoDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "memo")
public class Memo {
    @Id
    private String id;
    @Indexed
    private Long teacherId;
    @Field("created_time")
    private LocalDateTime createdTime;;
    @Field("updated_time") @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime updatedTime;
    @Field("is_deleted")
    private Boolean isDeleted;

    private String lesson;
    private List<Long> kids;
    private List<String> tags;
    private String content;

    Memo(MemoDto memoDto){
        this.teacherId = memoDto.getTeacherId();

        this.createdTime = memoDto.getCreatedTime()==null?LocalDateTime.now():memoDto.getCreatedTime();
        this.updatedTime = memoDto.getUpdatedTime()==null?this.createdTime:memoDto.getUpdatedTime();
        this.isDeleted = memoDto.getIsDeleted()==null?false:memoDto.getIsDeleted();

        this.lesson = memoDto.getLesson()==null?"":memoDto.getLesson();
        this.kids = memoDto.getKids()==null?new ArrayList<Long>():memoDto.getKids();
        this.tags = memoDto.getTags()==null?new ArrayList<String>():memoDto.getTags();
        this.content = memoDto.getContent()==null?"":memoDto.getContent();
    }
}
