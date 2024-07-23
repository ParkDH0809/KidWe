package yeomeong.common.repository.mongo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.jpa.member.Kid;
import yeomeong.common.entity.mongo.MongoMemo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MongoMemoRepository extends MongoRepository<MongoMemo, String>{
    @Query("{ 'kids': { $elemMatch: { $eq: ?0 } } }")
    List<MongoMemo> findMemoByKid(Kid kid);

    List<MongoMemo> findMemoByCreatedTime(LocalDateTime createdTime);


    @Query("{ 'kids': { $elemMatch: { $eq: ?0 } }, 'createdTime': ?1 }")
    List<MongoMemo> findByKidAndCreatedTime(Kid kid, LocalDateTime createdTime);
}
