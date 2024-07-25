package yeomeong.common.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "sequence")
public class MongoSequence {
    private String collectionName;
    private Integer index;

    public MongoSequence(String collectionName) {
        this.collectionName = collectionName;
        this.index = 0;
    }

    public void addIndex(){
        this.index+=1;
    }

    public Integer getIndex(){
        return this.index;
    }
}
