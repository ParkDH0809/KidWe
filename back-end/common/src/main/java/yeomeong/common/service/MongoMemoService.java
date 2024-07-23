package yeomeong.common.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yeomeong.common.entity.jpa.member.Kid;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.repository.mongo.MongoMemoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MongoMemoService {

    @Autowired
    private MongoMemoRepository mongoMemoRepository;

    // 메모 생성
    public MongoMemo createMongoMemo(MongoMemo mongoMemo) {
        return mongoMemoRepository.save(mongoMemo);
    }

    // 모든 메모 가져오기
    public List<MongoMemo> getAllMongoMemos() {
        return mongoMemoRepository.findAll();
    }

    // 메모ID로 메모 가져오기
    public Optional<MongoMemo> getMongoMemoById(String id) {
        return mongoMemoRepository.findById(id);
    }

    // 아이별 메모 가져오기
    public List<MongoMemo> getMongoMemoByKid(Kid kid) {
        return mongoMemoRepository.findMemoByKid(kid);
    }
    
    // 날짜별 메모 가져오기
    public List<MongoMemo> getMongoMemoByKid(LocalDateTime createdTime) {
        return mongoMemoRepository.findMemoByCreatedTime(createdTime);
    }

    // 아이&날짜별 메모 가져오기
    public List<MongoMemo> getMongoMemoByKidAndCreatedTime(Kid kid, LocalDateTime createdTime) {
        return mongoMemoRepository.findByKidAndCreatedTime(kid, createdTime);
    }

    // 메모 수정하기
    public MongoMemo updateMongoMemo(MongoMemo mongoMemo) {
        return mongoMemoRepository.save(mongoMemo);
    }

    // 메모 삭제하기
    public void deleteMongoMemo(String id) {
        mongoMemoRepository.deleteById(id);
    }
}