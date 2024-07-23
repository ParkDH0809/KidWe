package yeomeong.common.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoTeacherTag;

@Repository
public interface MongoTeacherMemoRepository extends MongoRepository<MongoTeacherTag, String> {

}
