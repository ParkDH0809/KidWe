package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.repository.MemoRepository;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    //
}
