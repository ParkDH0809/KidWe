package yeomeong.common.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter

@Document(collection = "tag")
public class Tag {

    @Id
    private String id;

    @Field(name = "teacher_id")
    @Indexed
    private Long teacherId;

    private String content;
    private String morpheme;
    private Long count;

    @Builder
    public Tag(Long teacherId, String content, String morpheme) {
        this.teacherId = teacherId;
        this.content = content;
        this.morpheme = morpheme;
        this.count = 1L;
    }
    public void count(){
        count += 1;
    }
}
