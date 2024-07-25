package yeomeong.common.repository;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoMemoPerDate;
import yeomeong.common.entity.mongo.MongoMemoPerTeacher;
import yeomeong.common.repository.mongo.MongoMemoPerTeacherRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class MongoMemoPerTeacherRepositoryTest {

    @Autowired
    private MongoMemoPerTeacherRepository repository;

    private MongoMemoPerTeacher mongoMemoPerTeacher;
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
        mongoMemoPerDate.setMemo(memo1);
        mongoMemoPerDate.setMemo(memo2);
        mongoMemoPerTeacher = new MongoMemoPerTeacher("teacher1", mongoMemoPerDate);
    }

    @Test
    public void testSaveAndFindById() {
        repository.save(mongoMemoPerTeacher);
        Optional<MongoMemoPerTeacher> found = repository.findById(mongoMemoPerTeacher.getId());
        assertTrue(found.isPresent());
        assertEquals(mongoMemoPerTeacher.getId(), found.get().getId());
        assertEquals(1, found.get().getMemos().size());

        repository.save(mongoMemoPerTeacher);
    }

    @Test
    public void testSaveAndFindAll() {
        repository.save(mongoMemoPerTeacher);
        Iterable<MongoMemoPerTeacher> allMemos = repository.findAll();
        assertTrue(allMemos.iterator().hasNext());

        repository.save(mongoMemoPerTeacher);
    }

    @Test
    public void testUpdateMemos() {
        repository.save(mongoMemoPerTeacher);
        mongoMemoPerTeacher.setMemoPerDate(LocalDate.now(), new MongoMemo("3", "Third memo"));
        repository.save(mongoMemoPerTeacher);
        Optional<MongoMemoPerTeacher> found = repository.findById(mongoMemoPerTeacher.getId());
        assertTrue(found.isPresent());
        MongoMemoPerDate foundMemoPerDate = found.get().getMemosPerDate(LocalDate.now());
        assertNotNull(foundMemoPerDate);
        assertEquals(3, foundMemoPerDate.getMemos().size());

        repository.save(mongoMemoPerTeacher);
    }

    @Test
    public void testDeleteById() {
        repository.save(mongoMemoPerTeacher);
        repository.deleteById(mongoMemoPerTeacher.getId());
        Optional<MongoMemoPerTeacher> found = repository.findById(mongoMemoPerTeacher.getId());
        assertFalse(found.isPresent());

        mongoMemoPerTeacher.setMemoPerDate(LocalDate.now(), memo1);
        mongoMemoPerTeacher.setMemoPerDate(LocalDate.now(), memo2);
        repository.save(mongoMemoPerTeacher);
    }
}
