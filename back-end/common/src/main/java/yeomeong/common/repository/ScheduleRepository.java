package yeomeong.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.Schedule;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {


    List<Schedule> findAllByKindergarten_IdAndEventDateAndScheduleTypeOrderByCreatedTimeDesc(Long kindergartenId, LocalDate localDate, Schedule.ScheduleType scheduleType);

    @Query("SELECT s FROM Schedule s WHERE s.ban.id = :banId AND s.eventDate = :eventDate AND (s.scheduleType = :scheduleType1 OR s.scheduleType = :scheduleType2) ORDER BY s.createdTime DESC")
    List<Schedule> findSchedules(Long banId, LocalDate eventDate, Schedule.ScheduleType scheduleType1, Schedule.ScheduleType scheduleType2);

    @Query("SELECT s "
        + "FROM Schedule s "
        + "WHERE s.ban.id = :banId "
        + "AND s.eventDate = :date")
    List<Schedule> findByBanIdAndDate(Long banId, LocalDate date);
}
