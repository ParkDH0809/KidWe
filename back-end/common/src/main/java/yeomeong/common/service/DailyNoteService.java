package yeomeong.common.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.dailynote.request.DailyNoteRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteListResponseDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteResponseDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
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
    public DailyNoteResponseDto createDailyNote(Long writerId, DailyNoteRequestDto dailyNoteCreateRequestDto) {
        Member writer = memberRepository.findById(writerId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_WRITER)
        );
        Kid kid = kidRepository.findById(dailyNoteCreateRequestDto.getKidId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_KID)
        );
        DailyNote createdDailyNote = dailyNoteRepository.save(dailyNoteCreateRequestDto.toEntity(kid, writer));
        return new DailyNoteResponseDto(createdDailyNote);
    }

    //월별 알림장 조회하기 - 학부모용
    @Transactional
    public DailyNoteListResponseDto getDailyNotesByKidId(Long guardianId, Long kidId, String yearAndMonth) {
        // 사용자가 존재하는지 확인하기
        Member member = memberRepository.findById(guardianId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_ID)
        );

        // 발신자로 된 알림장들
        List<DailyNote> writeDailyNotes = dailyNoteRepository.findByYearAndMonthAndKidId(yearAndMonth, guardianId, kidId);
        // 수신자로 된, 해당 아이의 선생님이 작성한 알림장 모두 조회
        List<DailyNote> receivedDailyNotes = dailyNoteRepository.findBYearAndMonthAndKidIdAndReceiverIsGuaridain(yearAndMonth, kidId);

        // 작성자인, 수신자인 알림장을 합쳐서 반환
        return new DailyNoteListResponseDto(writeDailyNotes, receivedDailyNotes);
    }

    //월별 알림장 조회하기 - 선생님, 원장님 용
    @Transactional
    public DailyNoteListResponseDto getDailyNotesByBanId(Long teacherId, Long banId, String yearAndMonth) {
        // 사용자가 존재하는지 확인하기
        Member member = memberRepository.findById(teacherId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_ID)
        );

        // 발신자로 된 알림장들
        List<DailyNote> writeDailyNotes = dailyNoteRepository.findByYearAndMonthAndBanId(yearAndMonth, teacherId, banId);
        // 수신자로 된, 반 아이들의 학부모가 작성한 알림장 모두 조회
        Ban ban = member.getBan();
        List<DailyNote> receivedDailyNotes = dailyNoteRepository.findByYearAndMonthAndBanAndReceiverIsTeacher(yearAndMonth,
                ban.getId());

        // 작성자인, 수신자인 알림장을 합쳐서 반환
        return new DailyNoteListResponseDto(writeDailyNotes, receivedDailyNotes);
    }

    // 알림장 상세정보 조회하기
    @Transactional
    public DailyNoteResponseDto getDailyNote(Long memberId, Long id) {
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_ID)
        );

        DailyNote dailyNote = dailyNoteRepository.findByDailyNoteId(id);
        if(dailyNote == null) throw new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID);
        // 발신자거나
        if(dailyNote.getWriter().getId().equals(member.getId())) {
            return new DailyNoteResponseDto(dailyNote);
        }
        // 전송시간이 지난 수신자거나
        else{
            if(member.getRole() == rtype.ROLE_TEACHER){
                if(dailyNote.getWriter().getRole() != rtype.ROLE_GUARDIAN || dailyNote.getSendTime().isBefore(LocalDateTime.now())){
                    throw new CustomException(ErrorCode.UNAUTHORIZED_WRITER);
                }
            }
            else if(member.getRole() == rtype.ROLE_GUARDIAN){
                if(dailyNote.getWriter().getRole() != rtype.ROLE_TEACHER || dailyNote.getSendTime().isBefore(LocalDateTime.now())){
                    throw new CustomException(ErrorCode.UNAUTHORIZED_WRITER);
                }
            }
        }
        throw new CustomException(ErrorCode.UNAUTHORIZED_WRITER);
    }

    // 알림장 수정하기
    @Transactional
    public DailyNoteResponseDto updateDailyNote(Long writerId, Long id, DailyNoteRequestDto updatedDailyNoteRequsetDto) {
        DailyNote oldDailyNote = dailyNoteRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        if(oldDailyNote.getWriter().getId() != writerId){
            throw new CustomException(ErrorCode.UNAUTHORIZED_WRITER);
        }
        oldDailyNote.setNewPost(updatedDailyNoteRequsetDto.getPost());
        oldDailyNote.setNewSendTime(updatedDailyNoteRequsetDto.getSendTime());
        return new DailyNoteResponseDto(dailyNoteRepository.save(oldDailyNote));
    }

    //알림장 삭제하기
    @Transactional
    public void deleteDailyNote(Long writerId, Long id) {
        Member writer = memberRepository.findById(writerId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_WRITER)
        );
        DailyNote oldDailyNote = dailyNoteRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        if(oldDailyNote.getWriter().getId() != writerId){
            throw new CustomException(ErrorCode.UNAUTHORIZED_WRITER);
        }
        oldDailyNote.delete();
        dailyNoteRepository.save(oldDailyNote);
    }
}
