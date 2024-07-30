package yeomeong.common.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "tag")
public class Tag {

    @Id
    private String id;

    @Field(name = "teacher_id")
    @Indexed
    private Long teacherId;

    private String content;
    private mtype morpheme;
    private Long count;
}
