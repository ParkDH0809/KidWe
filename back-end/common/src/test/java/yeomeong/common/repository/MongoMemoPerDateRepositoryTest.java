package yeomeong.common.repository;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoMemoPerDate;
import yeomeong.common.repository.mongo.MongoMemoPerDateRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class MongoMemoPerDateRepositoryTest {

    @Autowired
    private MongoMemoPerDateRepository repository;

    private MongoMemoPerDate mongoMemoPerDate;
    private MongoMemo memo1;
    private MongoMemo memo2;
    @Qualifier("mongo")
    @Autowired
    private MongoClient mongo;

    @BeforeEach
    public void setUp() {
        mongoMemoPerDate = new MongoMemoPerDate(LocalDate.now());
        memo1 = new MongoMemo("1", "First memo");
        memo2 = new MongoMemo("2", "Second memo");
        mongoMemoPerDate.setMemo(memo1);  // 초기화 시 메모 추가
        mongoMemoPerDate.setMemo(memo2);  // 초기화 시 메모 추가
    }

    @Test
    public void testSaveAndFindById() {
        repository.save(mongoMemoPerDate);
        Optional<MongoMemoPerDate> found = repository.findById(mongoMemoPerDate.getDate());
        assertTrue(found.isPresent());
        assertEquals(mongoMemoPerDate.getDate(), found.get().getDate());
        assertEquals(2, found.get().getMemos().size());  // 메모 개수 확인

        repository.save(mongoMemoPerDate);
    }

    @Test
    public void testSaveAndFindAll() {
        repository.save(mongoMemoPerDate);
        Iterable<MongoMemoPerDate> allMemos = repository.findAll();
        assertTrue(allMemos.iterator().hasNext());

        repository.save(mongoMemoPerDate);
    }

    @Test
    public void testUpdateMemos() {
        repository.save(mongoMemoPerDate);
        mongoMemoPerDate.setMemo(new MongoMemo("3", "Third memo"));
        repository.save(mongoMemoPerDate);
        Optional<MongoMemoPerDate> found = repository.findById(mongoMemoPerDate.getDate());
        assertTrue(found.isPresent());
        assertEquals(3, found.get().getMemos().size());  // 메모 개수 확인

        repository.save(mongoMemoPerDate);
    }

    @Test
    public void testDeleteById() {
        repository.save(mongoMemoPerDate);
        repository.deleteById(mongoMemoPerDate.getDate());
        Optional<MongoMemoPerDate> found = repository.findById(mongoMemoPerDate.getDate());
        assertFalse(found.isPresent());

        mongoMemoPerDate.setMemo(memo1);
        mongoMemoPerDate.setMemo(memo2);
        repository.save(mongoMemoPerDate);
    }
}
