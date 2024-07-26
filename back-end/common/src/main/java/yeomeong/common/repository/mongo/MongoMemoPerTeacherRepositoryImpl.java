package yeomeong.common.repository.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.member.Kid;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoMemoPerTeacher;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MongoMemoPerTeacherRepositoryImpl{
    @Autowired
    private MongoMemoPerTeacherRepository mongoMemoPerTeacherRepository;

    // Create or Update Memo
    public MongoMemo saveMemo(String teacherId, MongoMemo mongoMemo) {
        MongoMemoPerTeacher mongoMemoPerTeacher = mongoMemoPerTeacherRepository.findById(teacherId)
                .orElse(new MongoMemoPerTeacher(teacherId));

        LocalDate date = mongoMemo.getUpdatedTime().toLocalDate();
        mongoMemoPerTeacher.getMemos(date).removeIf(memo -> memo.getId().equals(mongoMemo.getId()));
        mongoMemoPerTeacher.getMemos(date).add(mongoMemo);

        mongoMemoPerTeacherRepository.save(mongoMemoPerTeacher);
        return mongoMemo;
    }

    // Read Memo by Date and ID
    public MongoMemo getMemo(String teacherId, LocalDate date, String memoId) {
        MongoMemoPerTeacher mongoMemoPerTeacher = mongoMemoPerTeacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 선생님의 메모를 찾을 수 없습니다."));

        return mongoMemoPerTeacher.getMemo(date, memoId);
    }

    // Read Memos by Date
    public List<MongoMemo> getMemosByDate(String teacherId, LocalDate date) {
        MongoMemoPerTeacher mongoMemoPerTeacher = mongoMemoPerTeacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 선생님의 메모를 찾을 수 없습니다."));

        return mongoMemoPerTeacher.getMemos(date);
    }

    // Read Memos by Date and Kid
    public List<MongoMemo> getMemosByDateAndKid(String teacherId, LocalDate date, Long kid) {
        MongoMemoPerTeacher mongoMemoPerTeacher = mongoMemoPerTeacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 선생님의 메모를 찾을 수 없습니다."));

        return mongoMemoPerTeacher.getMemosPerDateAndKid(date, kid);
    }

    // Delete Memo by Date and ID
    public void deleteMemo(String teacherId, LocalDate date, String memoId) {
        MongoMemoPerTeacher mongoMemoPerTeacher = mongoMemoPerTeacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 선생님의 메모를 찾을 수 없습니다."));

        mongoMemoPerTeacher.getMemos(date).removeIf(memo -> memo.getId().equals(memoId));
        mongoMemoPerTeacherRepository.save(mongoMemoPerTeacher);
    }
}
