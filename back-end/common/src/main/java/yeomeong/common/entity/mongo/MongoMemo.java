package yeomeong.common.entity.mongo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "memo")
public class MongoMemo {
    private String id;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime deletedTime;
    private String lesson;
    private Set<Long> kids = new HashSet<Long>();
    private Set<MongoTag> tags = new HashSet<MongoTag>();
    private String content;

    public MongoMemo() {

    }

    public MongoMemo(String id){
        this.id = id;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = createdTime;
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

    public void setDeletedTime(LocalDateTime deletedTime){
        this.deletedTime = deletedTime;
    }

    public LocalDateTime getDeletedTime(){
        return deletedTime;
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
}