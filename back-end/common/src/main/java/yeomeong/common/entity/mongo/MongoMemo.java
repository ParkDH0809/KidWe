package yeomeong.common.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import yeomeong.common.entity.jpa.member.Kid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "memo")
//@EnableMongoAuditing
//@EnableMongoRepositories
public class MongoMemo {
    @Id @Field("memo_id")
    private String id;

    private LocalDateTime createdTime;
//    private Integer createdYear;
//    private Integer createdMonth;
//    private Integer createdDay;

    private List<Kid> kids;

    private List<MongoTag> tags;

    private String content;

    // 아이 한명만 등록
    public void setKid(Kid kid) {
        if(kids==null)kids = new ArrayList<>();
        kids.add(kid);
    }

    // 아이 여러명 등록
    public void setKids(List<Kid> kids) {
        if(this.kids==null) this.kids = new ArrayList<>();
        this.kids.addAll(kids);
    }

    // 태그 하나만 등록 - 이미 있는 태그인 경우 count만 올려주기
    public void setTag(MongoTag tag) {
        // 코드 수정하기
        if(tags == null) tags = new ArrayList<>();
        tags.add(tag);
    }

    // 태그 여러개 등록 - 이미 있는 태그인 경우 count만 올려주기
    public void setTags(List<MongoTag> tags) {
        // 코드 수정하기
        if(this.tags == null) this.tags = new ArrayList<>();
        this.tags.addAll(tags);
    }
}