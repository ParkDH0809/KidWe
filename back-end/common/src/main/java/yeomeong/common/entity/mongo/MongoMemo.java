package yeomeong.common.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "memo")
public class MongoMemo {
    @Id @Field("memo_id")
    // String 아니고 Long이어도 되는지 확인하기
    private String id;

    private Integer createdYear;
    private Integer createdMonth;
    private Integer createdDay;
    private Integer createdHour;
    private Integer createdMinute;
    private Integer createdSecond;

    private Integer memoYear;
    private Integer memoMonth;
    private Integer memoDay;
    private Integer memoHour;
    private Integer memoMinute;
    private Integer memoSecond;

    private List<Long> kids;

    private List<MongoTag> tags;

    private String content;

    // 아이들 등록
    public void setKids(List<Long> kids) {
        if(this.kids==null) this.kids = new ArrayList<>();
        for(Long kidId : kids){
            if(!this.kids.contains((kidId))) this.kids.add(kidId);
        }
    }

    // 태그들 등록
    public void setTags(Long teacherId, List<MongoTag> tags) {
        // 유효한 선생님ID인지 확인해야 할까?
        if(this.tags == null) this.tags = new ArrayList<>();
        // 이미 서비스 딴에서 teacherTag도 각각 처리한 상태
        for(MongoTag tag : tags){
            if(!this.tags.contains(tag)) this.tags.add(tag);
        }
    }
}