package yeomeong.common.entity.mongo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

@RequiredArgsConstructor
public class MongoMemo {
    @Field("memo_id") @Indexed(unique = true)
    private String id;
    @Field("created_time")
    private LocalDateTime createdTime;;
    @Field("updated_time") @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime updatedTime;;
    @Field("is_deleted")
    private Boolean isDeleted;

    private String lesson;
    private List<Long> kids;
    private List<MongoTag> tags;
    private String content;

    public MongoMemo(String id){
        this.id = id;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = createdTime;
        this.kids = new LinkedList<Long>();
        this.tags = new LinkedList<MongoTag>();
        this.content = "";
    }

    public MongoMemo(String id, LocalDateTime updatedTime, String lesson, List<Long> kids, List<MongoTag> tags, String content){
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
    public List<Long> getKids(){
        return this.kids;
    }

    public void setTag(String teacherId, MongoTag tag) {
        this.tags.add(tag);
    }
    public void setTags(String teacherId, Set<MongoTag> tags) {
        this.tags.addAll(tags);
    }
    public List<MongoTag> getTags(){
        return this.tags;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }

    public LocalDate getDate(){
        return this.getUpdatedTime().toLocalDate();
    }

    public boolean isContainKid(Long kidId){
        if(kids.contains(kidId)) return true;
        return false;
    }

    public String toString(){
        return "ID : " + this.id + "\nTime : " + this.updatedTime + "\nLesson : " + this.lesson + "\nContent : " + this.content;
    }

    public boolean equals(MongoMemo memo) {
        if((memo == null) || (memo.getId() == null)) return false;
        return (this.id.equals(memo.getId()));
    }
}