package yeomeong.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.DailyNote;

@Repository
public interface DailyNoteRepository extends JpaRepository<DailyNote, Long>{

    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE dn.id = :id "
        + "AND dn.isDeleted = false")
    DailyNote findByDailyNoteId(@Param("id") Long id);

    // 전송 기준 : 작성자가 쓴 아이별 - 날짜별 알림장 (전송되지 않은 알림장까지 모두 보여줘야 한다)
    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE FUNCTION('DATE_FORMAT', dn.sendTime, '%Y-%m') = :date "
        + "AND dn.kid.id = :kidId "
        + "AND dn.writer.id = :writerId "
        + "AND dn.isDeleted = false")
    List<DailyNote> findByYearAndMonthAndKidId(@Param("date") String date,
        @Param("writerId") Long writerId,
        @Param("kidId") Long kidId);

    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE FUNCTION('DATE_FORMAT', dn.sendTime, '%Y-%m') = :date "
        + "AND dn.kid.ban.id = :banId "
        + "AND dn.writer.id = :writerId "
        + "AND dn.isDeleted = false")
    List<DailyNote> findByYearAndMonthAndBanId(@Param("date") String date,
        @Param("writerId") Long writerId,
        @Param("banId") Long banId);

    // 학부모 수신 : 학부모의 아이에게 선생님이 작성한 알림장을 조회
    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE FUNCTION('DATE_FORMAT', dn.sendTime, '%Y-%m') = :date "
        + "AND dn.kid.id = :kidId "
        + "AND dn.writer.role = 'ROLE_GUARDIAN' "
        + "AND dn.sendTime <= CURRENT_TIMESTAMP "
        + "AND dn.isDeleted = false")
    List<DailyNote> findBYearAndMonthAndKidIdAndReceiverIsGuaridain(@Param("date") String date,
        @Param("kidId") Long kidId);

    // 선생님 수신 : 담당반 아이들에게 학부모가 작성한 알림장을 조회
    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE FUNCTION('DATE_FORMAT', dn.post.createdDateTime, '%Y-%m') = :date "
        + "AND dn.kid.ban.kindergarten.id = :kindergartenId "
        + "AND dn.kid.ban.id = :banId "
        + "AND dn.writer.role != 'ROLE_GUARDIAN' "
        + "AND dn.sendTime <= CURRENT_TIMESTAMP "
        + "AND dn.isDeleted = false")
    List<DailyNote> findByYearAndMonthAndBanAndReceiverIsTeacher(@Param("date") String date,
        @Param("kindergartenId") Long kindergartenId,
        @Param("banId") Long banId);
}
