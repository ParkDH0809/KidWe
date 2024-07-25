package yeomeong.common.entity.mongo;

public class MongoSequenceIndex {
    private String collectionName;
    private Integer index;

    public MongoSequenceIndex(String collectionName) {
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
