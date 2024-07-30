package yeomeong.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.document.Memo;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemoRepository extends MongoRepository<Memo, String> {
    // 선생님별 날짜(updatedTime의 date만 추출)별 메모 가져오기
    @Query("{ 'teacher_id': ?0, 'date': ?1 }")
    List<Memo> findByTeacherIdAndDate(Long teacherId, LocalDate date);

    // 선생님별 날짜별 메모 중 해당 kid_id를 포함하고 있는 메모 가져오기
    @Query("{ 'teacher_id': ?0,  'date': ?1 , 'kids': ?2 }")
    List<Memo> findByTeacherIdAndDateAndKidId(Long teacherId, LocalDate date, Long kidId);
}
