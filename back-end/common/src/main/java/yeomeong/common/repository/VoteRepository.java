package yeomeong.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
