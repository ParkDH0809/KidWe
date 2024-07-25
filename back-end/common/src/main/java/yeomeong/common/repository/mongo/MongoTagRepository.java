package yeomeong.common.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoTag;

public interface MongoTagRepository extends MongoRepository<MongoTag, String> {
}
