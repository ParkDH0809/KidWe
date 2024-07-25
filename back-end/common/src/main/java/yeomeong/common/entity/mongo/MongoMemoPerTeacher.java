package yeomeong.common.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import yeomeong.common.entity.jpa.member.Kid;

import java.time.LocalDate;
import java.util.*;

@Document("memoperteacher")
public class MongoMemoPerTeacher {
    @Id
    private String id;
    private List<MongoMemoPerDate> memos = new ArrayList<MongoMemoPerDate>();

    public MongoMemoPerTeacher() {

    }

    public MongoMemoPerTeacher(String teacherId) {
        this.id = teacherId;
        this.memos = new ArrayList<MongoMemoPerDate>();
    }

    public MongoMemoPerTeacher(String teacherId, MongoMemoPerDate mongoMemoPerDate) {
        this.id = teacherId;
        this.memos = new ArrayList<MongoMemoPerDate>();
        memos.add(mongoMemoPerDate);
    }

    private MongoMemoPerDate fineMemoPerDate(LocalDate date) {
        for(MongoMemoPerDate memo : memos) {
            if(memo.getDate().equals(date)) {
                return memo;
            }
        }
        return null;
    }

    public void setMemos(MongoMemoPerDate mongoMemoPerDate) {
        memos.add(mongoMemoPerDate);
    }

    public List<MongoMemoPerDate> getMemos() {
        return memos;
    };

    public void setMemoPerDate(LocalDate date, MongoMemo mongoMemo) {
        MongoMemoPerDate memosPerDate = fineMemoPerDate(date);
        memosPerDate.setMemo(mongoMemo);
    }

    public void setMemosPerDate(LocalDate date, Set<MongoMemo> mongoMemos) {

        MongoMemoPerDate memosPerDate = fineMemoPerDate(date);
        memosPerDate.setMemos(mongoMemos);
    }

    public MongoMemoPerDate getMemosPerDate(LocalDate date){
        return fineMemoPerDate(date);
    }

    public Set<MongoMemo> getMemosPerDateAndKids(LocalDate date, Kid kids) {
        Set<MongoMemo> memosPerDateAndKids = new HashSet<MongoMemo>();

        MongoMemoPerDate memosPerDate = fineMemoPerDate(date);
        for(MongoMemo memo : memosPerDate.getMemos()){
            if(memo.getKids().contains(kids)){
                memosPerDateAndKids.add(memo);
            }
        }

        return memosPerDateAndKids;
    }
}
