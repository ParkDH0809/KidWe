package yeomeong.common.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.mongo.MongoMemo;

public interface MongoMemoRepository extends MongoRepository<MongoMemo, String> {
}
