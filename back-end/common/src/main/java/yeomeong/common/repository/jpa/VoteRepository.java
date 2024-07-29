package yeomeong.common.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.post.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
