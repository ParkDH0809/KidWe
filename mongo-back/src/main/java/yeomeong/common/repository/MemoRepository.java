package yeomeong.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.document.Memo;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemoRepository extends MongoRepository<Memo, String> {

    // 삭제되지 않은 경우에만 메모ID로 메모 가져오기
    @Query("{ '_id' : ?0, 'is_deleted' : false }")
    Memo findMemoById(String id);

    // 삭제되지 않은 경우에만 선생님의 메모ID로 메모 가져오기
    @Query("{ '_id' : ?0, 'teacher_id' : ?1, 'is_deleted' : false }")
    Memo findMemoByTeacherIdAndId(String id, Long teacherId);

    // 삭제되지 않은 경우에만 선생님별 날짜(updatedTime의 date만 추출)별 메모 가져오기
    @Query("{ 'teacher_id': ?0, 'date': ?1, 'is_deleted' : false}")
    List<Memo> findByTeacherIdAndDate(Long teacherId, String date);

    // 삭제되지 않은 경우에만 선생님별 날짜별 메모 중 해당 kid_id를 포함하고 있는 메모 가져오기
    // 중첩된 구조일 경우 elemMatch를 이용해 찾기
    @Query("{ 'teacher_id': ?0,  'date': ?1, 'is_deleted': false, 'kids': { '$elemMatch': { 'kid_id': ?2 } } }")
    List<Memo> findByTeacherIdAndDateAndKidId(Long teacherId, String date, Long kidId);
}
