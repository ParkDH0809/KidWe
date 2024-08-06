package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Repository
public interface DailyNoteCommentRepository extends JpaRepository<DailyNoteComment, Long> {
}
