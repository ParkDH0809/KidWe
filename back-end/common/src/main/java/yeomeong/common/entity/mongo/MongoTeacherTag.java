package yeomeong.common.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.List;

@Document(collection = "teachertag")
public class MongoTeacherTag {
    @Id @Field("teacher_id")
    // String 아니고 Long이어도 되는지 확인하기
    private String id;

    private List<MongoTag> tags;
}
