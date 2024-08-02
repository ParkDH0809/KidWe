package yeomeong.common.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.post.dailynote.DailyNoteRequestDto;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.repository.DailyNoteRepository;

@Service
@RequiredArgsConstructor
public class DailyNoteService {

    private final DailyNoteRepository dailyNoteRepository;

    //알림장 생성하기
    public DailyNote createDailyNote(DailyNoteRequestDto dailyNoteRequestDto) {
        return dailyNoteRepository.save(dailyNoteRequestDto.toEntity());
    }

    //날짜별&아이별 알림장 조회하기
    public List<DailyNote> getDailyNotes(Long kidId, String yearAndMonth) {
        return dailyNoteRepository.findByKidIdAndYearAndMonth(kidId, yearAndMonth);
    }

    //알림장 조회하기
    public DailyNote getDailyNote(Long id) {
        return dailyNoteRepository.findById(id).orElse(null);
    }

    //알림장 수정하기
    public DailyNote updateDailyNote(Long id, DailyNote updatedDailyNote) {
        Optional<DailyNote> isExistDailyNote = dailyNoteRepository.findById(
            updatedDailyNote.getId());
        DailyNote oldDailyNote;
        if (isExistDailyNote.isPresent()) {
            oldDailyNote = isExistDailyNote.get();
            // 업데이트 하기
            return dailyNoteRepository.save(oldDailyNote);
        }
        return null;
    }

    //알림장 삭제하기
    public boolean deleteDailyNote(Long id) {
        Optional<DailyNote> isExistDailyNote = dailyNoteRepository.findById(id);
        DailyNote oldDailyNote;
        if (isExistDailyNote.isPresent()) {
            oldDailyNote = isExistDailyNote.get();
            oldDailyNote.setIsDeleted(true);
            dailyNoteRepository.save(oldDailyNote);
            return true;
        }
        return false;
    }
}
