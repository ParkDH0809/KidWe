package yeomeong.common.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Document("tag")
@Getter
@Setter

public class MongoTag {
    private String id;

    private Integer createdYear;
    private Integer createdMonth;
    private Integer createdDay;
    private Integer createdHour;
    private Integer createdMinute;
    private Integer createdSecond;

    private Integer updatedYear;
    private Integer updatedMonth;
    private Integer updatedDay;
    private Integer updatedHour;
    private Integer updatedMinute;
    private Integer updatedSecond;

    private String content;

    private wtype morpheme;

    private Long count;
}
