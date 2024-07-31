package yeomeong.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
