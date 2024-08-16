package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.DailyNoteImage;

@Repository
public interface DailyNoteImageRepository extends JpaRepository<DailyNoteImage, Long> {
}
