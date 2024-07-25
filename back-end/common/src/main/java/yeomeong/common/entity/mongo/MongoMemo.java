package yeomeong.common.entity.mongo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "memo")
public class MongoMemo {
    @Indexed(unique = true)
    private String id;
    @Field("created_time")
    private LocalDateTime createdTime;;
    @Field("updated_time")
    private LocalDateTime updatedTime;;
    @Field("is_deleted") @Indexed(unique=true, background = true,)
    private Boolean isDeleted;

    private String lesson;
    private Set<Long> kids;
    private Set<MongoTag> tags;
    private String content;

    public MongoMemo() {
        this.isDeleted = false;
        this.kids = new HashSet<Long>();
        this.tags = new HashSet<MongoTag>();
    }

    public MongoMemo(String id){
        this.id = id;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = createdTime;
        this.kids = new HashSet<Long>();
        this.tags = new HashSet<MongoTag>();
        this.content = "";
    }

    public MongoMemo(String id, String content) {
        this.id = id;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = createdTime;
        this.content = content;
    }

    public MongoMemo(String id, LocalDateTime updatedTime, String lesson, Set<Long> kids, Set<MongoTag> tags, String content){
        this.id = id;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = updatedTime;
        this.lesson = lesson;
        this.kids = kids;
        this.tags = tags;
        this.content = content;
    }

    public String getId(){
        return id;
    }

    public LocalDateTime getCreatedTime(){
        return createdTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime){
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getUpdatedTime(){
        return updatedTime;
    }

    public void setIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }

    public Boolean getIsDeleted(){
        return isDeleted;
    }

    public void setLesson(String lesson){
        this.lesson = lesson;
    }

    public String getLesson(){
        return lesson;
    }

    public void setKid(Long kidId) {
        this.kids.add(kidId);
    }

    public void setKids(Set<Long> kidIds) {
        this.kids.addAll(kidIds);
    }

    public Set<Long> getKids(){
        return this.kids;
    }

    public void setTag(String teacherId, MongoTag tag) {
        this.tags.add(tag);
    }

    public void setTags(String teacherId, Set<MongoTag> tags) {
        this.tags.addAll(tags);
    }

    public Set<MongoTag> getTags(){
        return this.tags;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public boolean isContainKid(Long kidId){
        if(kids.contains(kidId)) return true;
        return false;
    }

    public boolean equals(MongoMemo memo) {
        if((memo == null) || (memo.getId() == null)) return false;
        return (this.id.equals(memo.getId()));
    }
}