package yeomeong.common.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.mongo.MongoTag;

public interface TagRepository extends MongoRepository<MongoTag, String> {

}
