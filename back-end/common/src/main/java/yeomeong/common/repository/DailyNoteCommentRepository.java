package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.comment.DailyNoteComment;

import java.util.List;

@Repository
public interface DailyNoteCommentRepository extends JpaRepository<DailyNoteComment, Long> {
    @Query("SELECT dnc FROM DailyNoteComment dnc ORDER BY dnc.createdTime")
    List<DailyNoteComment> findByDailyNoteId(Long dailyNoteId);
}
