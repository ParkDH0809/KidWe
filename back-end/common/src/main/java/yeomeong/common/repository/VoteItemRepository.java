package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.VoteItem;

@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem,Long> {
}
