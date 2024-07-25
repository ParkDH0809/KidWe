package yeomeong.common.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoTag;

public class MongoMemoTest {

    @Test
    public void testMongoMemoCreationWithIdOnly() {
        String testId = "testId";
        MongoMemo memo = new MongoMemo(testId);

        assertNotNull(memo.getId());
        assertEquals(testId, memo.getId());
        assertNotNull(memo.getCreatedTime());
        assertNotNull(memo.getUpdatedTime());
        assertEquals(memo.getCreatedTime(), memo.getUpdatedTime());
        assertTrue(memo.getKids().isEmpty());
        assertTrue(memo.getTags().isEmpty());
        assertEquals("", memo.getContent());
    }

    @Test
    public void testMongoMemoCreationWithFullConstructor() {
        String testId = "testId";
        LocalDateTime now = LocalDateTime.now();

        Set<Long> kids = new HashSet<>();
        kids.add(1L);
        kids.add(2L);

        Set<MongoTag> tags = new HashSet<>();

        String content = "Test content";

        MongoMemo memo = new MongoMemo(testId, now, "lesson",kids, tags, content);

        assertNotNull(memo.getId());
        assertEquals(testId, memo.getId());
        assertNotNull(memo.getCreatedTime());
        assertEquals(now, memo.getUpdatedTime());
        assertEquals(kids, memo.getKids());
        assertEquals(tags, memo.getTags());
        assertEquals(content, memo.getContent());
    }
}
