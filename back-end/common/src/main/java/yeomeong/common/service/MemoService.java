package yeomeong.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yeomeong.common.repository.jpa.MemoRepository;
import yeomeong.common.repository.mongo.MongoMemoRepository;

@Service

public class MemoService {
    @Autowired
    private MemoRepository memoRepository;
    @Autowired
    private MongoMemoRepository mongoMemoRepository;
}
