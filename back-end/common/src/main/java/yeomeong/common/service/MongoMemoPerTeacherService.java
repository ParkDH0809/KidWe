package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.entity.mongo.MongoMemo;
import yeomeong.common.entity.mongo.MongoMemoPerTeacher;
import yeomeong.common.repository.mongo.MongoMemoPerTeacherRepository;
import yeomeong.common.repository.mongo.MongoTagPerTeacherRepository;

// 선생님ID - 날짜 - List<메모>
@Service
@RequiredArgsConstructor
public class MongoMemoPerTeacherService {
    private final MongoTagPerTeacherRepository mongoTagPerTeacherRepository;
    private final MongoMemoPerTeacherRepository mongoMemoPerTeacherRepository;

    // 선생님별 메모 생성하기

    // 선생님별 메모 수정하기

    // 선생님별 메모 조회하기

    // 선생님별 메모 날짜별로 조회하기

    // 선생님별 메모 삭제하기
}
