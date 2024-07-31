package yeomeong.common.service;

import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.repository.DailyNoteRepository;

@Service
@RequiredArgsConstructor
public class DailyNoteService {
    private final DailyNoteRepository dailyNoteRepository;
    //알림장 생성하기
    public DailyNote createDailyNote(DailyNote dailyNote) {
        return dailyNoteRepository.save(dailyNote);
    }
    
    //날짜별&아이별 알림장 조회하기
    public List<DailyNote> getDailyNotes(Date date) {
        return null;
    }
    
    //아이별 알림장 모두 조회하기
    
    //알림장 수정하기
    
    //알림장 삭제하기
}
