package yeomeong.common.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.mongo.MongoMemoPerTeacher;

public interface MongoMemoPerTeacherRepository extends MongoRepository<MongoMemoPerTeacher, String>, MongoMemoPerTeacherRepositoryCustom {
    // 기본 쿼리 메서드들
}
