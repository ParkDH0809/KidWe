package yeomeong.common.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Document(collation = "tag")
@Setter
@Getter

public class MongoTag {
    private Long id;
    private String content;
    // 주어 or 서술어
}
