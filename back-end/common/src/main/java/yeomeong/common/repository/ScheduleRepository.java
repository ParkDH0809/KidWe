package yeomeong.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.Schedule;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {


    List<Schedule> findAllByBan_Kindergarten_IdAndEventDateAndScheduleTypeOrderByCreatedTimeDesc(Long kindergartenId, LocalDate localDate, Schedule.ScheduleType scheduleType);

    List<Schedule> findAllByBan_IdAndEventDateAndScheduleTypeOrScheduleTypeOrderByCreatedTimeDesc(Long banId, LocalDate localDate, Schedule.ScheduleType scheduleType1, Schedule.ScheduleType scheduleType2);

    @Query("SELECT s "
        + "FROM Schedule s "
        + "WHERE s.ban.id = :banId "
        + "AND s.eventDate = :date")
    List<Schedule> findByBanIdAndDate(Long banId, LocalDate date);
}
