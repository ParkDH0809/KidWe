package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.comment.AnnouncementComment;

@Repository
public interface AnnouncementCommentRepository extends JpaRepository<AnnouncementComment,Long> {
}
