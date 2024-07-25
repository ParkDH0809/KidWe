package yeomeong.common.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yeomeong.common.entity.mongo.MongoTag;
import yeomeong.common.entity.mongo.MongoTagPerTeacher;
import yeomeong.common.entity.mongo.wtype;
import yeomeong.common.repository.mongo.MongoTagPerTeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MongoTagPerTeacherServiceTest {

    @Autowired
    private MongoTagPerTeacherRepository mongoTagPerTeacherRepository;

    @Autowired
    private MongoTagPerTeacherService mongoTagPerTeacherService;

    @BeforeEach
    public void setUp() {
        mongoTagPerTeacherRepository.deleteAll();
    }

    @Test
    public void testUpdateTag_NewTeacher() {
        String teacherId = "teacher1";
        List<MongoTag> tags = new ArrayList<>();
        tags.add(new MongoTag("tag1", wtype.SUBJECT));
        tags.add(new MongoTag("tag2", wtype.SUBJECT));

        mongoTagPerTeacherService.updateTag(teacherId, tags);

        Optional<MongoTagPerTeacher> savedTeacher = mongoTagPerTeacherRepository.findById(teacherId);
        assertEquals(true, savedTeacher.isPresent());
        assertEquals(tags, savedTeacher.get().getTags());
    }

    @Test
    public void testUpdateTag_ExistingTeacher() {
        String teacherId = "teacher1";
        List<MongoTag> tags = new ArrayList<>();
        tags.add(new MongoTag("tag1", wtype.SUBJECT));
        tags.add(new MongoTag("tag2", wtype.SUBJECT));

        MongoTagPerTeacher existingTeacher = new MongoTagPerTeacher(teacherId, new ArrayList<>());
        mongoTagPerTeacherRepository.save(existingTeacher);

        mongoTagPerTeacherService.updateTag(teacherId, tags);

        Optional<MongoTagPerTeacher> updatedTeacher = mongoTagPerTeacherRepository.findById(teacherId);
        assertEquals(true, updatedTeacher.isPresent());
    }

    @Test
    public void testGetTagsByTeacherId_TeacherExists() {
        String teacherId = "teacher1";
        List<MongoTag> tags = new ArrayList<>();
        tags.add(new MongoTag("tag1", wtype.SUBJECT));
        tags.add(new MongoTag("tag2", wtype.SUBJECT));

        MongoTagPerTeacher existingTeacher = new MongoTagPerTeacher(teacherId, tags);
        mongoTagPerTeacherRepository.save(existingTeacher);

        List<MongoTag> retrievedTags = mongoTagPerTeacherService.getTagsByTeacherId(teacherId);

        assertEquals(tags, retrievedTags);
    }

    @Test
    public void testGetTagsByTeacherId_TeacherDoesNotExist() {
        String teacherId = "teacher1";

        List<MongoTag> retrievedTags = mongoTagPerTeacherService.getTagsByTeacherId(teacherId);

        assertEquals(0, retrievedTags.size());
    }
}
