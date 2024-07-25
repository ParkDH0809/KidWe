package yeomeong.common.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Setter
@Getter
@Document("tag")
public class MongoTag {
    @Id @Field("tag_id")
    private String id;
    @Field("created_time")
    private LocalDateTime createdTime;
    @Field("updated_time")
    private LocalDateTime updatedTime;
    private String content;
    private wtype morpheme;
    private Long count;

    MongoTag(){

    }

    // 태그 생성 시
    public MongoTag(String id, String content, wtype morpheme){
        this.id = id;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = createdTime;
        this.content = content;
        this.morpheme = morpheme;
        this.count = 1L;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
         this.updatedTime = updatedTime;
    }

    public LocalDateTime getUpdatedTime() {
        return this.updatedTime;
    }

    public String getContent() {
        return this.content;
    }

    public String getMorpheme() {
        return this.morpheme.name();
    }

    public void setCount(){
        this.count += 1L;
    }

    public Long getCount(){
        return this.count;
    }

    public Boolean equals(MongoTag mongoTag){
        return this.content.equals(mongoTag.getContent());
    }
}
