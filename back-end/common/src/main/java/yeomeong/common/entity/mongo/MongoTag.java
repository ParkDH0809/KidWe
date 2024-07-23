package yeomeong.common.entity.mongo;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDateTime;

@Document("tag")
@Getter
@Setter

//@EnableMongoAuditing
//@EnableMongoRepositories
public class MongoTag {
    private String id;

    private LocalDateTime createTime;
//    private Integer createdYear;
//    private Integer createdMonth;
//    private Integer createdDay;
//    private Integer createdHour;
//    private Integer createdMinute;
//    private Integer createdSecond;

    private String content;
    private wtype morpheme;

    private Long count;
}
