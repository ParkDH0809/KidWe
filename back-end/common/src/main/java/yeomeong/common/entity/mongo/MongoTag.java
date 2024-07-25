package yeomeong.common.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Objects;

@Document("tag")
public class MongoTag {
    @Field("created_time")
    private LocalDateTime createdTime;
    @Field("updated_time")
    private LocalDateTime updatedTime;
    private String content;
    private wtype morpheme;
    private Long count;

    // 태그 생성 시
    public MongoTag(String content, wtype morpheme) {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = createdTime;
        this.content = content;
        this.morpheme = morpheme;
        this.count = 1L;
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

    public void setCount() {
        this.count += 1L;
    }

    public Long getCount() {
        return this.count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoTag mongoTag = (MongoTag) o;
        return Objects.equals(content, mongoTag.content) &&
                morpheme == mongoTag.morpheme;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, morpheme);
    }
}
