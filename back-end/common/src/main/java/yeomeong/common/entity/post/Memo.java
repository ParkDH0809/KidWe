package yeomeong.common.entity.post;

import org.springframework.data.mongodb.core.mapping.Document;
import java.sql.Time;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Kid;

@Document(collection = "memo")
@Setter
@Getter

public class Memo {
    private Long id;
    private Time createdTime;
    private List<Kid> kids;
    private List<Tag> tags;
    private String content;
}
