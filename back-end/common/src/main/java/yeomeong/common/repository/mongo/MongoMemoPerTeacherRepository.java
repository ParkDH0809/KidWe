package yeomeong.common.repository.mongo;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.jpa.member.Kid;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoMemoPerTeacher;

import java.time.LocalDate;
import java.util.List;


public interface MongoMemoPerTeacherRepository extends MongoRepository<MongoMemoPerTeacher, String> {
    // Create or Update Memo
    public MongoMemo saveMemo(String teacherId, MongoMemo mongoMemo);

    // Read Memo by Date and ID
    public MongoMemo getMemo(String teacherId, LocalDate date, String memoId);

    // Read Memos by Date
    public List<MongoMemo> getMemosByDate(String teacherId, LocalDate date);

    // Read Memos by Date and Kid
    public List<MongoMemo> getMemosByDateAndKid(String teacherId, LocalDate date, Long kid);

    // Delete Memo by Date and ID
    public void deleteMemo(String teacherId, LocalDate date, String memoId);
}