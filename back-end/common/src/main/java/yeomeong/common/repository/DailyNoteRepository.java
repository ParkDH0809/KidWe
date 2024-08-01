package yeomeong.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.repository.querydsl.DailyNoteRepositoryCustom;

@Repository
public interface DailyNoteRepository extends JpaRepository<DailyNote, Long>,
    DailyNoteRepositoryCustom {

    @Query("SELECT dn FROM DailyNote dn WHERE dn.kid.id = :kidId AND FUNCTION('DATE_FORMAT', dn.post.createdDateTime, '%Y-%m') = :date")
    List<DailyNote> findByKidIdAndYearAndMonth(@Param("kidId") Long kidId,
        @Param("date") String date);
}
