package yeomeong.common.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import yeomeong.common.dto.Kid;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "memo")
public class Memo {
    @Id
    private String id;
    @Field("teacher_id") @Indexed
    private Long teacherId;
    @Field("created_time")
    private LocalDateTime createdTime;;
    @Field("updated_time") @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime updatedTime;
    private String date;
    @Field("is_deleted")
    private Boolean isDeleted;

    private String lesson;
    private List<Kid> kids;
    private List<Tag> tags;
    private String content;
}
