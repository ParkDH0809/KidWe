package yeomeong.common.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yeomeong.common.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a " +
        "JOIN a.kid k " +
        "WHERE k.ban.id = :banId " +
        "AND k.isDeleted = false " +
        "AND a.date = :date")
    List<Attendance> findAttendancesByBanIdAndDate(@Param("banId") Long banId,
        @Param("date") LocalDate date);

}
