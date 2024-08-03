package yeomeong.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.Schedule;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {


    List<Schedule> findAllByBan_Kindergarten_IdAndEventDateAndScheduleTypeOrderByEventTimeDesc(Long kindergartenId, LocalDate localDate, Schedule.ScheduleType scheduleType);

    List<Schedule> findAllByBan_IdAndEventDateAndScheduleTypeOrderByEventTimeDesc(Long banId, LocalDate localDate, Schedule.ScheduleType scheduleType);
}
