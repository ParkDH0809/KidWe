package yeomeong.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.post.comment.AnnouncementComment;

@Repository
public interface AnnouncementCommentRepository extends JpaRepository<AnnouncementComment,Long> {
}
