package yeomeong.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.post.dailynote.request.DailyNoteRequestDto;
import yeomeong.common.dto.post.dailynote.response.AutoCreateDailyNoteResponseDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteListResponseDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteGuardianResponseDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteTeacherResponseDto;
import yeomeong.common.entity.Schedule;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.DailyNoteImage;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.*;
import yeomeong.common.util.FileUtil;

import static yeomeong.common.util.FileUtil.uploadFileToS3;
import static yeomeong.common.util.FileUtil.uploadOriginalAdnThumbnailToS3;

@Service
@RequiredArgsConstructor
public class DailyNoteService {
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final MemberRepository memberRepository;
    private final KidRepository kidRepository;
    private final DailyNoteRepository dailyNoteRepository;
    private final DailyNoteImageRepository dailyNoteImageRepository;
    private final ScheduleRepository scheduleRepository;
    private final AmazonS3 s3Client;

    // 알림장 생성하기
    @Transactional
    public Object createDailyNote(Long writerId,
                                  DailyNoteRequestDto dailyNoteCreateRequestDto,
                                  List<MultipartFile> images) throws Exception {
        Member writer = memberRepository.findById(writerId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_WRITER)
        );
        Kid kid = kidRepository.findById(dailyNoteCreateRequestDto.getKidId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_KID)
        );

        DailyNote createdDailyNote = dailyNoteRepository.save(dailyNoteCreateRequestDto.toEntity(kid, writer));

        if(images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String imageUrl = uploadOriginalAdnThumbnailToS3(s3Client, bucketName, image, 100, 100);
                if(imageUrl != null) {
                    DailyNoteImage dailyNoteImage = new DailyNoteImage(imageUrl, createdDailyNote);
                    dailyNoteImageRepository.save(dailyNoteImage);
                }
            }
        }

        // 학부모라면
        if(writer.getRole() == rtype.ROLE_GUARDIAN){
            return new DailyNoteGuardianResponseDto(dailyNoteRepository.findByDailyNoteId(createdDailyNote.getId()));
        }
        // 선생님이라면
        else if (writer.getRole() == rtype.ROLE_TEACHER){
            return new DailyNoteTeacherResponseDto(dailyNoteRepository.findByDailyNoteId(createdDailyNote.getId()));
        }
        // 원장님이라면
        else {
            throw new CustomException(ErrorCode.UNAUTHORIZED_WRITER);
        }
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
    public Object getDailyNote(Long memberId, Long id) {
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_ID)
        );

        DailyNote dailyNote = dailyNoteRepository.findByDailyNoteId(id);
        if(dailyNote == null) throw new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID);

        // 발신자인 경우
        if(dailyNote.getWriter().getId().equals(member.getId())) {
            // 학부모라면
            if(dailyNote.getWriter().getRole() == rtype.ROLE_GUARDIAN){
                return new DailyNoteGuardianResponseDto(dailyNote);
            }
            // 선생님이라면
            else if(dailyNote.getWriter().getRole() == rtype.ROLE_TEACHER){
                return new DailyNoteTeacherResponseDto(dailyNote);
            }
            else {
                throw new CustomException(ErrorCode.UNAUTHORIZED_WRITER);
            }
        }
        // 전송시간이 지난 수신자인 경우
        else{
            if(member.getRole() == rtype.ROLE_GUARDIAN){
                if(dailyNote.getWriter().getRole() != rtype.ROLE_TEACHER || dailyNote.getSendTime().isBefore(LocalDateTime.now())){
                    throw new CustomException(ErrorCode.UNAUTHORIZED_RECEIVER);
                }
                return new DailyNoteTeacherResponseDto(dailyNote);
            }

            else{
                if(dailyNote.getWriter().getRole() != rtype.ROLE_GUARDIAN || dailyNote.getSendTime().isBefore(LocalDateTime.now())){
                    throw new CustomException(ErrorCode.UNAUTHORIZED_RECEIVER);
                }
                return new DailyNoteTeacherResponseDto(dailyNote);
            }
        }
    }

    // 알림장 수정하기
    @Transactional
    public DailyNoteGuardianResponseDto updateDailyNote(Long writerId, Long id, DailyNoteRequestDto updatedDailyNoteRequsetDto) {
        DailyNote oldDailyNote = dailyNoteRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );
        if(oldDailyNote.getWriter().getId() != writerId){
            throw new CustomException(ErrorCode.UNAUTHORIZED_WRITER);
        }
        oldDailyNote.setNewContent(updatedDailyNoteRequsetDto.getContent());
        oldDailyNote.setNewSendTime(updatedDailyNoteRequsetDto.getSendTime());
        return new DailyNoteGuardianResponseDto(dailyNoteRepository.save(oldDailyNote));
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

    // 알림장 자동 생성을 위한 정보 조회
    @Transactional
    public AutoCreateDailyNoteResponseDto getInfoForAutoCreateDailyNote(Long teacherId, Long kidId){
        Member teacher = memberRepository.findById(teacherId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_ID)
        );
        Kid kid = kidRepository.findById(kidId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_KID)
        );

        List<Schedule> schedules = scheduleRepository.findByBanIdAndDate(teacher.getBan().getId(), LocalDate.now());
        return new AutoCreateDailyNoteResponseDto(teacher, kid, schedules);
    }
}
