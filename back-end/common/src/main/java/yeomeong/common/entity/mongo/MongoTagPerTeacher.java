package yeomeong.common.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Document(collation = "tagperteacher")
public class MongoTagPerTeacher {
    private String id;
    private Set<MongoTag> tags;

    MongoTagPerTeacher() {

    }

    MongoTagPerTeacher(String id, Set<MongoTag> tags) {
        this.id = id;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setTag(MongoTag tag) {
        this.tags.add(tag);
    }

    public void setTags(Set<MongoTag> tags) {
        this.tags.addAll(tags);
    }

    public Set<MongoTag> getTags() {
        return tags;
    }

    public void useTag(MongoTag tag) {
        for(MongoTag t : tags) {
            if(t.equals(tag)) {
                t.addCount();
            }
        }
    }
}