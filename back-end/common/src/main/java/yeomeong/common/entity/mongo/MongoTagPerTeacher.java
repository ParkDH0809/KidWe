package yeomeong.common.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.LinkedList;

@Document(collection = "tagperteacher")
public class MongoTagPerTeacher {
    @Id
    @Field("teacher_id")
    private String id;
    private List<MongoTag> tags;

    MongoTagPerTeacher() {

    }

    public MongoTagPerTeacher(String id) {
        this.id = id;
        this.tags = new LinkedList<MongoTag>();
    }

    public MongoTagPerTeacher(String id, List<MongoTag> tags) {
        this.id = id;
        this.tags = new LinkedList<MongoTag>();
        this.tags.addAll(tags);
    }

    public String getId() {
        return id;
    }

    public void setTag(MongoTag tag) {
        if(!tags.contains(tag)) {
            tags.add(tag);
        }
        else{
            for(MongoTag targetTag : tags) {
                if(targetTag.equals(tag)) targetTag.setCount();
            }
        }
    }

    public void setTags(List<MongoTag> tags) {
        for(MongoTag targetTag : tags) {
            setTag(targetTag);
        }
    }

    public List<MongoTag> getTags() {
        return tags;
    }
}