package yeomeong.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.DailyNote;

@Repository
public interface DailyNoteRepository extends JpaRepository<DailyNote, Long>{

    //삭제되지 않은 알림장 상세조회
    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE dn.id = :id "
        + "AND dn.isDeleted = false")
    DailyNote findByDailyNoteId(@Param("id") Long id);

    // 학부모 발신 기준 알림장 목록 조회 : 학부모가 쓴 아이별 - 날짜별 알림장 (전송되지 않은 알림장까지 모두 보여줘야 한다)
    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE FUNCTION('DATE_FORMAT', dn.sendTime, '%Y-%m') = :date "
        + "AND dn.kid.id = :kidId "
        + "AND dn.writer.id = :writerId "
        + "AND dn.isDeleted = false")
    List<DailyNote> findByYearAndMonthAndWriterIsGuardian(@Param("date") String date,
        @Param("writerId") Long writerId,
        @Param("kidId") Long kidId);

    // 선생님 발신 기준 알림장 목록 조회 : 담당 반별 - 날짜별 알림장 (전송되지 않은 알림장까지 모두 보여줘야 한다)
    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE FUNCTION('DATE_FORMAT', dn.sendTime, '%Y-%m') = :date "
        + "AND dn.kid.ban.id = :banId "
        + "AND dn.writer.id = :writerId "
        + "AND dn.isDeleted = false")
    List<DailyNote> findByYearAndMonthAndWriterIsTeacher(@Param("date") String date,
        @Param("writerId") Long writerId,
        @Param("banId") Long banId);

    // 학부모 수신 기준 알림장 목록 조회 : 학부모의 아이에게 선생님이 작성한 알림장을 조회
    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE FUNCTION('DATE_FORMAT', dn.sendTime, '%Y-%m') = :date "
        + "AND dn.kid.id = :kidId "
        + "AND dn.writer.role = 'ROLE_TEACHER' "
    // 한국시간으로 비교하기
        + "AND dn.sendTime <= CONVERT_TZ(CURRENT_TIMESTAMP, 'UTC', 'Asia/Seoul') "
        + "AND dn.isDeleted = false")
    List<DailyNote> findBYearAndMonthAndKidIdAndReceiverIsGuardian(@Param("date") String date,
        @Param("kidId") Long kidId);

    // 선생님 수신 기준 알림장 목록 조회 : 담당반 아이들에게 학부모가 작성한 알림장을 조회
    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE FUNCTION('DATE_FORMAT', dn.sendTime, '%Y-%m') = :date "
        + "AND dn.kid.ban.id = :banId "
        + "AND dn.writer.role = 'ROLE_GUARDIAN' "
        // 한국시간으로 비교하기
        + "AND dn.sendTime <= CONVERT_TZ(CURRENT_TIMESTAMP, 'UTC', 'Asia/Seoul') "
        + "AND dn.isDeleted = false")
    List<DailyNote> findByYearAndMonthAndBanAndReceiverIsTeacher(@Param("date") String date,
        @Param("banId") Long banId);

    // 원장님 기준 알림장 목록 조회 : 담당 반별 - 날짜별 알림장
    @Query("SELECT dn "
            + "FROM DailyNote dn "
            + "WHERE FUNCTION('DATE_FORMAT', dn.sendTime, '%Y-%m') = :date "
            + "AND dn.kid.ban.id = :banId "
            // 한국시간으로 비교하기
            + "AND dn.sendTime <= CONVERT_TZ(CURRENT_TIMESTAMP, 'UTC', 'Asia/Seoul') "
            + "AND dn.isDeleted = false")
    List<DailyNote> findByYearAndMonthAndIsDirector(@Param("date") String date,
                                                 @Param("banId") Long banId);

}
