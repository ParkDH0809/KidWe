package yeomeong.common.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import yeomeong.common.dto.post.dailynote.request.DailyNoteCreateRequestDto;
import yeomeong.common.dto.post.dailynote.request.DailyNoteUpdateRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteResponseDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
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
    public List<DailyNoteResponseDto> getDailyNotes(Long memberId, Long kidId, String yearAndMonth) {
        Member receiver = memberRepository.findById(memberId).orElse(null);
        if(receiver != null) {
            List<DailyNoteResponseDto> dailyNoteResponseDtos = new ArrayList<>();
            // 내가 쓴 글
            List<DailyNote> writeDailyNotes = dailyNoteRepository.findByKidIdAndYearAndMonthAndWriterId(memberId, kidId, yearAndMonth);
            List<DailyNote> receiveDailyNotes = new ArrayList<>();
            if(receiver.getRole() == rtype.ROLE_TEACHER){
                // 상대방이 써준 글
                receiveDailyNotes = dailyNoteRepository.findByKidIdAndYearAndMonthAndReceiverType(rtype.ROLE_GUARDIAN, kidId, yearAndMonth);
            }
            else if(receiver.getRole() == rtype.ROLE_GUARDIAN){
                // 상대방이 써준 글
                receiveDailyNotes = dailyNoteRepository.findByKidIdAndYearAndMonthAndReceiverType(rtype.ROLE_TEACHER, kidId, yearAndMonth);
            }

            for(DailyNote dailyNote : writeDailyNotes) {
                dailyNoteResponseDtos.add(new DailyNoteResponseDto(dailyNote));
            }
            for(DailyNote dailyNote : receiveDailyNotes){
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
            return new DailyNoteResponseDto(dailyNote);
        }
        return null;
    }

    //알림장 수정하기
    public DailyNoteResponseDto updateDailyNote(DailyNoteUpdateRequestDto updatedDailyNoteRequsetDto) {
        DailyNote oldDailyNote = dailyNoteRepository.findById(updatedDailyNoteRequsetDto.getId()).orElse(null);
        if (oldDailyNote != null) {
            oldDailyNote.setPost(updatedDailyNoteRequsetDto.getPost());
            oldDailyNote.setSendTime(updatedDailyNoteRequsetDto.getSendTime());
            return new DailyNoteResponseDto(dailyNoteRepository.save(oldDailyNote));
        }
        return null;
    }

    //알림장 삭제하기
    public boolean deleteDailyNote(Long id) {
        DailyNote oldDailyNote = dailyNoteRepository.findById(id).orElse(null);
        if (oldDailyNote != null) {
            oldDailyNote.setIsDeleted(true);
            dailyNoteRepository.save(oldDailyNote);
            return true;
        }
        return false;
    }
}
