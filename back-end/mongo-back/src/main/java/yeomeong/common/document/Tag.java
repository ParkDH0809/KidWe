package yeomeong.common.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor

@Document(collection = "tag")
public class Tag {

    @Id
    private String id;

    @Field(name = "teacher_id")
    @Indexed
    private Long teacherId;

    private String content;
    private mtype morpheme;
    @Field(name = "is_first")
    private Integer isFirst;
    private Long count;

    @JsonIgnore
    private final static mtype [] order = new mtype [] {mtype.NNG, mtype.NNP, mtype.NNB, mtype.VV, mtype.VA, mtype.NF, mtype.NV};

    @Builder
    public Tag(Long teacherId, String content, String morpheme) {
        this.teacherId = teacherId;
        this.content = content;
        this.morpheme = mtype.valueOf(morpheme);
        this.isFirst = 2;
        for(int o=0; o<7; o++){
            if(morpheme.equals(order[o])){
                this.isFirst = 1;
                break;
            }
        }
        this.count = 1L;
    }

    @Builder
    public Tag(String id, Long teacherId, String content, String morpheme, Integer isFirst, Long count) {
        this.teacherId = teacherId;
        this.content = content;
        this.morpheme = mtype.valueOf(morpheme);
        this.isFirst = 2;
        for(int o=0; o<7; o++){
            if(morpheme.equals(order[o])){
                this.isFirst = 1;
                break;
            }
        }
        this.count = 1L;
    }

    public void count(){
        count += 1;
    }
}
