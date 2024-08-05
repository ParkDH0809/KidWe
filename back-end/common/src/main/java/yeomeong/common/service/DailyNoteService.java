package yeomeong.common.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.dailynote.request.DailyNoteCreateRequestDto;
import yeomeong.common.dto.post.dailynote.request.DailyNoteUpdateRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteResponseDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.exception.ErrorResponse;
import yeomeong.common.repository.DailyNoteRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyNoteService {

    private final DailyNoteRepository dailyNoteRepository;
    private final MemberRepository memberRepository;
    private final KidRepository kidRepository;

    //알림장 생성하기
    @Transactional
    public DailyNoteResponseDto createDailyNote(DailyNoteCreateRequestDto dailyNoteCreateRequestDto) {
        Long writerId = dailyNoteCreateRequestDto.getWriterId();
        Member writer = memberRepository.findById(writerId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_WRITER)
        );
        Kid kid = kidRepository.findById(dailyNoteCreateRequestDto.getKidId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_KID)
        );
        DailyNote createdDailyNote = dailyNoteRepository.save(dailyNoteCreateRequestDto.toEntity(kid, writer));
        return new DailyNoteResponseDto(createdDailyNote);
    }

    //날짜별&아이별 알림장 조회하기
    public List<DailyNoteResponseDto> getDailyNotes(Long memberId, Long kidId, String yearAndMonth) {
        Member receiver = memberRepository.findById(memberId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_ID)
        );

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

    //알림장 조회하기
    public DailyNoteResponseDto getDailyNote(Long id) {
        DailyNote dailyNote = dailyNoteRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        return new DailyNoteResponseDto(dailyNote);
    }

    //알림장 수정하기
    @Transactional
    public DailyNoteResponseDto updateDailyNote(DailyNoteUpdateRequestDto updatedDailyNoteRequsetDto) {
        DailyNote oldDailyNote = dailyNoteRepository.findById(updatedDailyNoteRequsetDto.getId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        oldDailyNote.setPost(updatedDailyNoteRequsetDto.getPost());
        oldDailyNote.setSendTime(updatedDailyNoteRequsetDto.getSendTime());
        return new DailyNoteResponseDto(dailyNoteRepository.save(oldDailyNote));
    }

    //알림장 삭제하기
    @Transactional
    public void deleteDailyNote(Long id) {
        DailyNote oldDailyNote = dailyNoteRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        oldDailyNote.setIsDeleted(true);
        dailyNoteRepository.save(oldDailyNote);
    }
}
