package yeomeong.common.repository;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yeomeong.common.entity.Attendance;

@Transactional
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a JOIN a.kid k WHERE k.ban.id = :banId  AND k.isDeleted = false AND a.date = :date")
    List<Attendance> findAttendancesByBanIdAndDate(@Param("banId") Long banId, @Param("date") LocalDate date);

    @Modifying
    @Query("UPDATE Attendance a SET a.attendedToday = :attendedToday WHERE a.kid.id = :kidId AND a.date = :date")
    void updateKidsAttendanceState(@Param("kidId") Long kidId, @Param("date") LocalDate date, @Param("attendedToday") Boolean attendedToday);
}
