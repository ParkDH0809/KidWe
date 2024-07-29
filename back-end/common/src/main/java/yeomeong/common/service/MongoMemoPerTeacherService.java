package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.repository.mongo.MongoMemoPerTeacherRepository;
import yeomeong.common.repository.mongo.MongoTagPerTeacherRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoMemoPerTeacherService {
    private final MongoTagPerTeacherRepository mongoTagPerTeacherRepository;
    private final MongoMemoPerTeacherRepository mongoMemoPerTeacherRepository;

    // 선생님별 메모 생성하기
    public MongoMemo createMemo(String teacherId, MongoMemo mongoMemo) {
        return mongoMemoPerTeacherRepository.saveMemo(teacherId, mongoMemo);
    }

    // 선생님별 메모 수정하기
    public MongoMemo updateMemo(String teacherId, MongoMemo mongoMemo) {
        return mongoMemoPerTeacherRepository.saveMemo(teacherId, mongoMemo);
    }

    // 선생님별 메모 조회하기
    public MongoMemo getMemo(String teacherId, LocalDate date, String memoId) {
        return mongoMemoPerTeacherRepository.getMemo(teacherId, date, memoId);
    }

    // 선생님별 메모 날짜별로 조회하기
    public List<MongoMemo> getMemosByDate(String teacherId, LocalDate date) {
        return mongoMemoPerTeacherRepository.getMemosByDate(teacherId, date);
    }

    // 선생님별 메모 삭제하기
    public void deleteMemo(String teacherId, LocalDate date, String memoId) {
        mongoMemoPerTeacherRepository.deleteMemo(teacherId, date, memoId);
    }
}
