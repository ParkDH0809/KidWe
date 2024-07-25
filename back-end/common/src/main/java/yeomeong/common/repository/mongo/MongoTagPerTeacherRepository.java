package yeomeong.common.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.mongo.MongoTagPerTeacher;

public interface MongoTagPerTeacherRepository extends MongoRepository<MongoTagPerTeacher, String> {

}