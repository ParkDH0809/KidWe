package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.entity.post.comment.DailyNoteComment;
import yeomeong.common.repository.DailyNoteCommentRepository;

@Service
@RequiredArgsConstructor
public class DailyNoteCommentService {
    private final DailyNoteCommentRepository dailyNoteCommentRepository;
    // 알림장에 (대)댓글 생성하기
    public DailyNoteComment createDailyNoteComment(DailyNoteComment dailyNoteComment){
        return dailyNoteCommentRepository.save(dailyNoteComment);
    }

    // 알림장에 (대)댓글 조회하기 <-- 알림장 조회해갈때 가능하지 않나???

    // 알림장에 (대)댓글 수정하기
    public DailyNoteComment updateDailyNoteComment(DailyNoteComment dailyNoteComment){
        return dailyNoteCommentRepository.save(dailyNoteComment);
    }
    
    // 알림장에 (대)댓글 삭제하기
    public DailyNoteComment deleteDailyNoteComment(DailyNoteComment dailyNoteComment){
        dailyNoteComment.setIsDeleted(true);
        return dailyNoteCommentRepository.save(dailyNoteComment);
    }
}
