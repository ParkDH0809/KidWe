package yeomeong.common.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.post.Memo;

@Repository
public interface MemoRepository extends MongoRepository<Memo, String>{
}
