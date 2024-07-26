package yeomeong.common.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
