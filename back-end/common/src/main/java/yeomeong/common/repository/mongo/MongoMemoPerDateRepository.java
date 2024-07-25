package yeomeong.common.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.mongo.MongoMemoPerDate;

import java.time.LocalDate;

public interface MongoMemoPerDateRepository extends MongoRepository<MongoMemoPerDate, LocalDate> {
}
