package yeomeong.common.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yeomeong.common.entity.post.Memo;
import yeomeong.common.entity.post.MongoMemo;
import yeomeong.common.repository.MemoRepository;
import yeomeong.common.repository.MongoMemoRepository;

@Service

public class MemoService {
    @Autowired
    private MemoRepository memoRepository;
    @Autowired
    private MongoMemoRepository mongoMemoRepository;
}
