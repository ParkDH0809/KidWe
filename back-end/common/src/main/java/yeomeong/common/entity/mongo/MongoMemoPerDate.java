package yeomeong.common.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "memoperdate")
public class MongoMemoPerDate {
    @Id
    private final LocalDate date;
    private Set<MongoMemo> memos = new HashSet<>();;

    @ConstructorProperties({"date"})
    public MongoMemoPerDate(LocalDate date) {
        this.date = date;
        this.memos = new HashSet<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setMemo(MongoMemo memo) {
        this.memos.add(memo);
    }

    public void setMemos(Set<MongoMemo> memos) {
        this.memos.addAll(memos);
    }

    public Set<MongoMemo> getMemos() {
        return memos;
    }
}
