package yeomeong.common.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.dailynote.request.DailyNoteCreateRequestDto;
import yeomeong.common.dto.post.dailynote.request.DailyNoteUpdateRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteListResponseDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteResponseDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.comment.DailyNoteComment;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.exception.ErrorResponse;
import yeomeong.common.repository.DailyNoteCommentRepository;
import yeomeong.common.repository.DailyNoteRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyNoteService {

    private final MemberRepository memberRepository;
    private final KidRepository kidRepository;
    private final DailyNoteRepository dailyNoteRepository;
    private final DailyNoteCommentRepository dailyNoteCommentRepository;

    // 알림장 생성하기
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

    //월별 알림장 조회하기
    @Transactional
    public DailyNoteListResponseDto getDailyNotes(Long memberId, Long kidId, String yearAndMonth) {
        // 수신인이 존재하는지 확인하기
        Member receiver = memberRepository.findById(memberId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_ID)
        );

        // 작성자로 된 알림장들
        List<DailyNote> writeDailyNotes = dailyNoteRepository.findByYearAndMonthAndKidIdAndWriterId(yearAndMonth, kidId, memberId);
        List<DailyNote> receivedDailyNotes = new ArrayList<>();

        // 수신자로 된 알림장들
        if(receiver.getRole() == rtype.ROLE_TEACHER){
            // 수신자가 선생님일 경우 담당 반 아이들의 학부모가 작성한 알림장 모두 조회
            Ban ban = receiver.getBan();
            receivedDailyNotes = dailyNoteRepository.findByYearAndMonthAndBanAndReceiverType(yearAndMonth,
                ban.getKindergarten().getId(),
                ban.getId(),
                rtype.ROLE_GUARDIAN);
        }
        else if(receiver.getRole() == rtype.ROLE_GUARDIAN){
            // 수신자가 학부모일 경우 해당 아이의 선생님이 작성한 알림장 모두 조회
            receivedDailyNotes = dailyNoteRepository.findBYearAndMonthAndKidIdAndReceiverType(yearAndMonth,
                kidId,
                rtype.ROLE_TEACHER);
        }

        // 작성자인, 수신자인 알림장을 합쳐서 반환
        return new DailyNoteListResponseDto(yearAndMonth, writeDailyNotes, receivedDailyNotes);
    }

    // 알림장 상세정보 조회하기
    @Transactional
    public DailyNoteResponseDto getDailyNote(Long id) {
        DailyNote dailyNote = dailyNoteRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        return new DailyNoteResponseDto(dailyNote);
    }

    // 알림장 수정하기
    @Transactional
    public DailyNoteResponseDto updateDailyNote(DailyNoteUpdateRequestDto updatedDailyNoteRequsetDto) {
        DailyNote oldDailyNote = dailyNoteRepository.findById(updatedDailyNoteRequsetDto.getId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        oldDailyNote.setNewPost(updatedDailyNoteRequsetDto.getPost());
        oldDailyNote.setNewSendTime(updatedDailyNoteRequsetDto.getSendTime());
        return new DailyNoteResponseDto(dailyNoteRepository.save(oldDailyNote));
    }

    //알림장 삭제하기
    @Transactional
    public void deleteDailyNote(Long id) {
        DailyNote oldDailyNote = dailyNoteRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        oldDailyNote.delete();
        dailyNoteRepository.save(oldDailyNote);
    }
}
