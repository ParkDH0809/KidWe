package yeomeong.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yeomeong.common.entity.mongo.MongoTag;
import yeomeong.common.entity.mongo.MongoTagPerTeacher;
import yeomeong.common.repository.mongo.MongoTagPerTeacherRepository;

import java.util.*;

@Service
public class MongoTagPerTeacherService {
    private final MongoTagPerTeacherRepository mongoTagPerTeacherRepository;

    @Autowired
    public MongoTagPerTeacherService(MongoTagPerTeacherRepository mongoTagPerTeacherRepository) {
        this.mongoTagPerTeacherRepository = mongoTagPerTeacherRepository;
    }

    // 선생님별 태그 업데이트하기
    public void updateTag(String teacherId, List<MongoTag> candTags) {
        MongoTagPerTeacher mongoTagPerTeacher = mongoTagPerTeacherRepository.findById(teacherId)
                .orElseGet(() -> new MongoTagPerTeacher(teacherId, candTags));

        mongoTagPerTeacher.setTags(candTags);
        mongoTagPerTeacherRepository.save(mongoTagPerTeacher);
    }

    // 선생님별 태그 조회하기
    public List<MongoTag> getTagsByTeacherId(String teacherId) {
        return mongoTagPerTeacherRepository.findById(teacherId)
                .map(MongoTagPerTeacher::getTags)
                .orElseGet(ArrayList::new);
    }
}