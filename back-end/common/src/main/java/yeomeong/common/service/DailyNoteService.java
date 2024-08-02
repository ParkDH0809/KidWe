package yeomeong.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import yeomeong.common.dto.post.dailynote.request.DailyNoteCreateRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteResponseDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.repository.DailyNoteRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class DailyNoteService {

    private final DailyNoteRepository dailyNoteRepository;
    private final MemberRepository memberRepository;
    private final KidRepository kidRepository;

    //알림장 생성하기
    public DailyNoteResponseDto createDailyNote(DailyNoteCreateRequestDto dailyNoteCreateRequestDto) {
        Long writerId = dailyNoteCreateRequestDto.getWriterId();
        Member writer = memberRepository.findById(writerId).orElse(null);
        if(writer != null) {
            Kid kid = kidRepository.findById(dailyNoteCreateRequestDto.getKidId()).orElse(null);
            if(kid != null){
                DailyNote createdDailyNote = dailyNoteRepository.save(dailyNoteCreateRequestDto.toEntity(kid, writer));
                return new DailyNoteResponseDto(createdDailyNote);
            }
            return null;
        }
        return null;
    }

    //날짜별&아이별 알림장 조회하기
    public List<DailyNoteResponseDto> getDailyNotes(Long kidId, String yearAndMonth) {
        List<DailyNote> dailyNotes = dailyNoteRepository.findByKidIdAndYearAndMonth(kidId, yearAndMonth);
        if(dailyNotes != null){
            List<DailyNoteResponseDto> dailyNoteResponseDtos = new ArrayList<>();
            for(DailyNote dailyNote : dailyNotes){
                dailyNoteResponseDtos.add(new DailyNoteResponseDto(dailyNote));
            }
            return dailyNoteResponseDtos;
        }
        return null;
    }

    //알림장 조회하기
    public DailyNoteResponseDto getDailyNote(Long id) {
        DailyNote dailyNote = dailyNoteRepository.findById(id).orElse(null);
        if(dailyNote != null){
            DailyNoteResponseDto dailyNoteResponseDto = new DailyNoteResponseDto(dailyNote);
            return dailyNoteResponseDto;
        }
        return null;
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
