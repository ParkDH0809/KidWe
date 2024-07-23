package yeomeong.common.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.member.Kid;

@Document(collection = "memo")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MongoMemo {
    private String id;
    private LocalDateTime createdTime;
    private List<Kid> kids;
    private List<MongoTag> tags;
    private String content;

    public void setKid(Kid kid) {
        if(kids==null)kids = new ArrayList<>();
        kids.add(kid);
    }
}