package yeomeong.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.DailyNote;

@Repository
public interface DailyNoteRepository extends JpaRepository<DailyNote, Long>{

    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE dn.writer.id = :writerId AND dn.kid.id = :kidId AND FUNCTION('DATE_FORMAT', dn.post.createdDateTime, '%Y-%m') = :date")
    List<DailyNote> findByKidIdAndYearAndMonthAndWriterId(@Param("writerId") Long writerId,
        @Param("kidId") Long kidId,
        @Param("date") String date);

    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE dn.writer.role = :writerType AND dn.kid.id = :kidId AND FUNCTION('DATE_FORMAT', dn.post.createdDateTime, '%Y-%m') = :date AND dn.sendTime <= CURRENT_TIMESTAMP")
    List<DailyNote> findByKidIdAndYearAndMonthAndReceiverType(@Param("writerType") rtype role,
        @Param("kidId") Long kidId,
        @Param("date") String date);

    @Query("SELECT dn "
        + "FROM DailyNote dn "
        + "WHERE dn.kid.id = :kidId AND FUNCTION('DATE_FORMAT', dn.post.createdDateTime, '%Y-%m') = :date")
    List<DailyNote> findByKidIdAndYearAndMonth(@Param("kidId") Long kidId,
        @Param("date") String date);
}
