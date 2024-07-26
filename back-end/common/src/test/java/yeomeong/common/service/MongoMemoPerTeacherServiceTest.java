package yeomeong.common.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoMemoPerTeacher;
import yeomeong.common.repository.mongo.MongoMemoPerTeacherRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class MongoMemoPerTeacherServiceTest {

    @Autowired
    private MongoMemoPerTeacherRepository mongoMemoPerTeacherRepository;

    @Test
    public void testSaveMemo() {
        MongoMemo mongoMemo = new MongoMemo();
        // MongoMemo 필드 설정
        MongoMemoPerTeacher mongoMemoPerTeacher = new MongoMemoPerTeacher();
        mongoMemoPerTeacher.setMemo(mongoMemo);

        mongoMemoPerTeacherRepository.save(mongoMemoPerTeacher);

        MongoMemoPerTeacher updatedMemoPerTeacher = mongoMemoPerTeacherRepository.findById("1").orElse(null);
        assertNotNull(updatedMemoPerTeacher);
    }

    @Test
    public void testUpdateMemo() {
        MongoMemo mongoMemo = new MongoMemo();
        // MongoMemo 필드 설정
        MongoMemoPerTeacher mongoMemoPerTeacher = new MongoMemoPerTeacher();
        mongoMemoPerTeacher.setMemo(mongoMemo);

        mongoMemoPerTeacherRepository.save(mongoMemoPerTeacher);

        // 업데이트 로직 추가
        mongoMemoPerTeacherRepository.save(mongoMemoPerTeacher);

        MongoMemoPerTeacher updatedMemoPerTeacher = mongoMemoPerTeacherRepository.findById("1").orElse(null);
        assertNotNull(updatedMemoPerTeacher);
    }

    @Test
    public void testDeleteMemo() {
        MongoMemo mongoMemo1 = new MongoMemo();
        MongoMemo mongoMemo2 = new MongoMemo();
        // MongoMemo 필드 설정
        MongoMemoPerTeacher memoPerTeacher1 = new MongoMemoPerTeacher();
        MongoMemoPerTeacher memoPerTeacher2 = new MongoMemoPerTeacher();
        memoPerTeacher1.setMemo(mongoMemo1);
        memoPerTeacher2.setMemo(mongoMemo2);

        mongoMemoPerTeacherRepository.save(memoPerTeacher1);
        mongoMemoPerTeacherRepository.save(memoPerTeacher2);

        mongoMemoPerTeacherRepository.deleteById("1");

        MongoMemoPerTeacher deletedMemoPerTeacher = mongoMemoPerTeacherRepository.findById("1").orElse(null);
        assertNull(deletedMemoPerTeacher);
    }
}
