package yeomeong.common.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoTag;
import yeomeong.common.repository.mongo.MongoMemoRepository;

@SpringBootTest
public class MongoMemoRepositoryTest {

    @Autowired
    private MongoMemoRepository repository;

    @Test
    public void testSaveAndFindById() {
        String testId = "testId";
        LocalDateTime updateTime = LocalDateTime.now();

        Set<Long> kids = new HashSet<>();
        kids.add(1L);
        kids.add(2L);

        Set<MongoTag> tags = new HashSet<>();

        String content = "Test content";

        MongoMemo memo = new MongoMemo(testId, updateTime, kids, tags, content);

        // 저장
        MongoMemo savedMemo = repository.save(memo);
        assertNotNull(savedMemo);
        assertEquals(testId, savedMemo.getId());

        // ID로 조회
        MongoMemo foundMemo = repository.findById(testId).orElse(null);
        assertNotNull(foundMemo);
        assertEquals(testId, foundMemo.getId());
        assertEquals(content, foundMemo.getContent());

        // 저장
        MongoMemo createdMemo = repository.save(new MongoMemo("100", updateTime, kids, tags, content));
    }

    @Test
    public void testDelete() {
        String testId = "testId";
        MongoMemo memo = new MongoMemo(testId);

        // 저장
        repository.save(memo);

        // 삭제
        repository.deleteById(testId);

        // 조회
        MongoMemo foundMemo = repository.findById(testId).orElse(null);
        assertNull(foundMemo);
    }
}
