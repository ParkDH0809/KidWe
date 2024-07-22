package yeomeong.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import yeomeong.common.entity.post.Memo;
import yeomeong.common.entity.post.Tag;

public interface TagRepository extends MongoRepository<Tag, String> {

}
