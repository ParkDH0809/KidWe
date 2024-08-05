package yeomeong.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Repository
public interface DailyNoteCommentRepository extends JpaRepository<DailyNoteComment, Long> {
    @Query("SELECT dnc FROM DailyNoteComment dnc WHERE dnc.parentComment.id = :id")
    List<DailyNoteComment> findByParentCommentId(Long id);
}
