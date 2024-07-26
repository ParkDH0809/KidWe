package yeomeong.common.repository.mongo;

import yeomeong.common.entity.mongo.MongoMemo;

import java.time.LocalDate;
import java.util.List;

public interface MongoMemoPerTeacherRepositoryCustom {
    // Create or Update Memo
    MongoMemo saveMemo(String teacherId, MongoMemo mongoMemo);

    // Read Memo by Date and ID
    MongoMemo getMemo(String teacherId, LocalDate date, String memoId);

    // Read Memos by Date
    List<MongoMemo> getMemosByDate(String teacherId, LocalDate date);

    // Read Memos by Date and Kid
    List<MongoMemo> getMemosByDateAndKid(String teacherId, LocalDate date, Long kid);

    // Delete Memo by Date and ID
    void deleteMemo(String teacherId, LocalDate date, String memoId);
}
