package yeomeong.common.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import yeomeong.common.entity.mongo.MongoTag;
import yeomeong.common.entity.mongo.MongoTagPerTeacher;
import yeomeong.common.repository.mongo.MongoTagPerTeacherRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class MongoTagPerTeacherRepositoryTest {

    @Autowired
    private MongoTagPerTeacherRepository repository;

    private MongoTagPerTeacher mongoTagPerTeacher;
    private MongoTag tag1;
    private MongoTag tag2;

    @BeforeEach
    public void setUp() {
        tag1 = new MongoTag("Tag1");
        tag2 = new MongoTag("Tag2");
        Set<MongoTag> tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);
        mongoTagPerTeacher = new MongoTagPerTeacher("teacher1", tags);
    }

    @Test
    public void testSaveAndFindById() {
        repository.save(mongoTagPerTeacher);
        Optional<MongoTagPerTeacher> found = repository.findById(mongoTagPerTeacher.getId());
        assertTrue(found.isPresent());
        assertEquals(mongoTagPerTeacher.getId(), found.get().getId());
        assertEquals(2, found.get().getTags().size());
    }

    @Test
    public void testUpdateTags() {
        repository.save(mongoTagPerTeacher);
        MongoTag newTag = new MongoTag("Tag3");
        mongoTagPerTeacher.setTag(newTag);
        repository.save(mongoTagPerTeacher);
        Optional<MongoTagPerTeacher> found = repository.findById(mongoTagPerTeacher.getId());
        assertTrue(found.isPresent());
        assertEquals(3, found.get().getTags().size());
    }

    @Test
    public void testUseTag() {
        repository.save(mongoTagPerTeacher);
        mongoTagPerTeacher.useTag(tag1);
        repository.save(mongoTagPerTeacher);
        Optional<MongoTagPerTeacher> found = repository.findById(mongoTagPerTeacher.getId());
        assertTrue(found.isPresent());
        Set<MongoTag> tags = found.get().getTags();
        for (MongoTag tag : tags) {
            if (tag.equals(tag1)) {
                assertEquals(1, tag.getCount());
            }
        }
    }

    @Test
    public void testDeleteById() {
        repository.save(mongoTagPerTeacher);
        repository.deleteById(mongoTagPerTeacher.getId());
        Optional<MongoTagPerTeacher> found = repository.findById(mongoTagPerTeacher.getId());
        assertFalse(found.isPresent());
    }
}
