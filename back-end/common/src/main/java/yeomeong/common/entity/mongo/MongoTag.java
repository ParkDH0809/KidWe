package yeomeong.common.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Document("tag")
public class MongoTag {
    private String id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String content;
    private wtype morpheme;
    private Long count;

    MongoTag(){

    }

    public MongoTag(String id){
        this.id = id;
        createdTime = LocalDateTime.now();
        updatedTime = createdTime;
        content = "";
        morpheme = null;
        count = 0L;
    }

    public long addCount(){
        return this.count += 1;
    }
}
