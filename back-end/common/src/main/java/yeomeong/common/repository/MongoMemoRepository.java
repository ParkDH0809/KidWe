package yeomeong.common.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.post.Memo;
import yeomeong.common.entity.post.MongoMemo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MongoMemoRepository extends MongoRepository<MongoMemo, Long>{
    @Query("{ 'kids': { $elemMatch: { $eq: ?0 } } }")
    List<MongoMemo> findMemoByKid(Kid kid);

    List<MongoMemo> findMemoByCreatedTime(LocalDateTime createdTime);


    @Query("{ 'kids': { $elemMatch: { $eq: ?0 } }, 'createdTime': ?1 }")
    List<MongoMemo> findByKidAndCreatedTime(Kid kid, LocalDateTime createdTime);
}
