package yeomeong.common.entity.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import yeomeong.common.entity.jpa.member.Kid;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Document(collection = "memoperteacher")
public class MongoMemoPerTeacher {
    private String id;
    private Map<LocalDate, List<MongoMemo>> memos = new HashMap<>();

    public MongoMemoPerTeacher(String teacherId) {
        this.id = teacherId;
        this.memos = new HashMap<LocalDate, List<MongoMemo>>();
    }

    public String getId() {
        return id;
    }

    public void setMemo(MongoMemo mongoMemo) {

    }

    public List<MongoMemo> getMemos(LocalDate date) {
        if(!memos.containsKey(date)) return null;
        return memos.get(date);
    }

    public MongoMemo getMemo(LocalDate date, String id) {
        if(!memos.containsKey(date)) return null;
        for(MongoMemo targetMemo : memos.get(date)) {
            if(targetMemo.getId().equals(id)) return targetMemo;
        }
        return null;
    }

    public List<MongoMemo> getMemosPerDateAndKid(LocalDate date, Long kid) {
        if(!memos.containsKey(date)) return null;

        List<MongoMemo> memosPerDateAndKids = new LinkedList<MongoMemo>();
        for(MongoMemo memo : memos.get(date)){
            if(memo.getKids().contains(kid)){
                memosPerDateAndKids.add(memo);
            }
        }

        return memosPerDateAndKids;
    }
}
