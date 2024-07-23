package yeomeong.common.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Document(collection = "teachertag")
public class MongoTeacherTag {
    @Id
    private BigInteger teacherId;

    private List<MongoTag> tags;
}
