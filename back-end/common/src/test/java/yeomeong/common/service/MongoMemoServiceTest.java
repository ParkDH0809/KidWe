package yeomeong.common.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import yeomeong.common.entity.jpa.member.Kid;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.repository.mongo.MongoMemoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MongoMemoServiceTest {

    @Autowired
    private MongoMemoService mongoMemoService;

    @Autowired
    private MongoMemoRepository mongoMemoRepository;

    @BeforeEach
    public void setup() {
        mongoMemoRepository.deleteAll();
    }

    @Test
    public void testCreateMongoMemo() {
        MongoMemo memo = new MongoMemo();
        memo.setContent("Test Memo");
        memo.setCreatedTime(LocalDateTime.now());

        MongoMemo savedMemo = mongoMemoService.createMongoMemo(memo);
        assertNotNull(savedMemo.getId());
        assertEquals("Test Memo", savedMemo.getContent());
    }

    @Test
    public void testGetAllMongoMemos() {
        MongoMemo memo1 = new MongoMemo();
        memo1.setContent("Memo 1");
        memo1.setCreatedTime(LocalDateTime.now());

        MongoMemo memo2 = new MongoMemo();
        memo2.setContent("Memo 2");
        memo2.setCreatedTime(LocalDateTime.now());

        mongoMemoService.createMongoMemo(memo1);
        mongoMemoService.createMongoMemo(memo2);

        List<MongoMemo> memos = mongoMemoService.getAllMongoMemos();
        assertEquals(2, memos.size());
    }

    @Test
    public void testGetMongoMemoById() {
        MongoMemo memo = new MongoMemo();
        memo.setContent("Test Memo");
        memo.setCreatedTime(LocalDateTime.now());

        MongoMemo savedMemo = mongoMemoService.createMongoMemo(memo);
        Optional<MongoMemo> foundMemo = mongoMemoService.getMongoMemoById(savedMemo.getId());
        assertTrue(foundMemo.isPresent());
        assertEquals("Test Memo", foundMemo.get().getContent());
    }

    @Test
    public void testGetMongoMemoByKid() {
        Kid kid = new Kid(); // Kid 객체를 적절히 설정해야 합니다.
        kid.setId(1L);

        MongoMemo memo = new MongoMemo();
        memo.setContent("Test Memo");
        memo.setCreatedTime(LocalDateTime.now());
        memo.setKid(kid);

        mongoMemoService.createMongoMemo(memo);

        List<MongoMemo> memos = mongoMemoService.getMongoMemoByKid(kid);
        assertEquals(1, memos.size());
        assertEquals("Test Memo", memos.get(0).getContent());
    }

    @Test
    public void testGetMongoMemoByCreatedTime() {
        LocalDateTime createdTime = LocalDateTime.now();
        MongoMemo memo = new MongoMemo();
        memo.setContent("Test Memo");
        memo.setCreatedTime(createdTime);

        mongoMemoService.createMongoMemo(memo);

        List<MongoMemo> memos = mongoMemoService.getMongoMemoByKid(createdTime);
        assertEquals(1, memos.size());
        assertEquals("Test Memo", memos.get(0).getContent());
    }

    @Test
    public void testUpdateMongoMemo() {
        MongoMemo memo = new MongoMemo();
        memo.setContent("Original Memo");
        memo.setCreatedTime(LocalDateTime.now());

        MongoMemo savedMemo = mongoMemoService.createMongoMemo(memo);
        savedMemo.setContent("Updated Memo");

        MongoMemo updatedMemo = mongoMemoService.updateMongoMemo(savedMemo);
        assertEquals("Updated Memo", updatedMemo.getContent());
    }

    @Test
    public void testDeleteMongoMemo() {
        MongoMemo memo = new MongoMemo();
        memo.setContent("Test Memo");
        memo.setCreatedTime(LocalDateTime.now());

        MongoMemo savedMemo = mongoMemoService.createMongoMemo(memo);
        mongoMemoService.deleteMongoMemo(savedMemo.getId());

        Optional<MongoMemo> foundMemo = mongoMemoService.getMongoMemoById(savedMemo.getId());
        assertFalse(foundMemo.isPresent());
    }
}
