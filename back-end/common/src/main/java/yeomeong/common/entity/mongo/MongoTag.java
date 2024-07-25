package yeomeong.common.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Document("tag")
public class MongoTag {
    private String id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String content;
    private wtype morpheme;
    private Long count;

    public long addCount(){
        return this.count += 1;
    }
}
