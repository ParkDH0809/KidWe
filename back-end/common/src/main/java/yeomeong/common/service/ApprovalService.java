package yeomeong.common.service;

import com.amazonaws.services.s3.AmazonS3;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.approval.AcceptRequestDto;
import yeomeong.common.dto.approval.ApprovalRequestDto;
import yeomeong.common.dto.approval.PendingKidResponseDto;
import yeomeong.common.dto.member.TeacherDetailInfoResponseDto;
import yeomeong.common.dto.approval.KidJoinKindergartenRequestDto;
import yeomeong.common.dto.approval.TeacherJoinKindergartenRequestDto;
import yeomeong.common.dto.notification.NotificationRequestDto;
import yeomeong.common.entity.member.Approval;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.KidMember;
import yeomeong.common.entity.member.atype;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.ApprovalRepository;
import yeomeong.common.repository.AttendanceRepository;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidMemberRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;
import yeomeong.common.util.FileUtil;
import yeomeong.common.util.NotificationUtil;

@Service
@Slf4j
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final MemberRepository memberRepository;
    private final BanRepository banRepository;
    private final KidRepository kidRepository;
    private final KindergartenRepository kindergartenRepository;
    private final KidMemberRepository kidMemberRepository;
    private final AmazonS3 amazonS3;
    private final AttendanceRepository attendanceRepository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public ApprovalService(ApprovalRepository approvalRepository, MemberRepository memberRepository, BanRepository banRepository,
        KidRepository kidRepository, KindergartenRepository kindergartenRepository, KidMemberRepository kidMemberRepository,
        AmazonS3 amazonS3, AttendanceRepository attendanceRepository) {
        this.approvalRepository = approvalRepository;
        this.memberRepository = memberRepository;
        this.banRepository = banRepository;
        this.kidRepository = kidRepository;
        this.kindergartenRepository = kindergartenRepository;
        this.kidMemberRepository = kidMemberRepository;
        this.amazonS3 = amazonS3;
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    public void applyForKindergartenByTeacher(TeacherJoinKindergartenRequestDto teacherJoinRequestDto) {
        ApprovalRequestDto approvalRequestDto = new ApprovalRequestDto(
            memberRepository.findById(teacherJoinRequestDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)),
            banRepository.findById(teacherJoinRequestDto.getBanId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)),
            kindergartenRepository.findById(teacherJoinRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID))
        );
        approvalRepository.save(ApprovalRequestDto.toApprovalEntity(approvalRequestDto));
        memberRepository.updateMemberStatus(teacherJoinRequestDto.getMemberId(), atype.PENDING);
    }

    @Transactional
    public void applyForKindergartenByGuardian(KidJoinKindergartenRequestDto kidJoinKindergartenRequestDto, MultipartFile picture) {
        String pictureName = null;
        if (picture != null && !picture.isEmpty()) {
            pictureName = FileUtil.uploadFileToS3(amazonS3, bucketName, picture);
        }
        long kidId = kidRepository.save(KidJoinKindergartenRequestDto.toKidEntity(kidJoinKindergartenRequestDto, pictureName)).getId();
        ApprovalRequestDto approvalRequestDto = new ApprovalRequestDto(
            kidRepository.findById(kidId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)),
            banRepository.findById(kidJoinKindergartenRequestDto.getBanId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID)),
            kindergartenRepository.findById(kidJoinKindergartenRequestDto.getKindergartenId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID))
        );
        approvalRepository.save(ApprovalRequestDto.toApprovalEntity(approvalRequestDto));
        kidMemberRepository.save(
            KidMember.builder()
                .kid(kidRepository.findById(kidId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
                .member(memberRepository.findById(kidJoinKindergartenRequestDto.getMemberId())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE)))
                .build()
        );

        if (memberRepository.findById(kidJoinKindergartenRequestDto.getMemberId()).get().getMemberStatus() != atype.ACCEPT) {
            memberRepository.updateMemberStatus(kidJoinKindergartenRequestDto.getMemberId(), atype.PENDING);
        }
    }

    public List<TeacherDetailInfoResponseDto> getPendingTeachers(Long kindergartenId) {
        List<TeacherDetailInfoResponseDto> teacherDetailInfoResponseDtos = new ArrayList<>();
        approvalRepository.findByKindergartenIdAndMemberIdIsNotNull(kindergartenId)
            .forEach(m -> teacherDetailInfoResponseDtos.add(TeacherDetailInfoResponseDto.toTeacherDetailResponseDto(m)));
        return teacherDetailInfoResponseDtos;
    }

    public List<TeacherDetailInfoResponseDto> getAcceptTeachers(Long kindergartenId) {
        List<TeacherDetailInfoResponseDto> teacherDetailInfoResponseDtos = new ArrayList<>();
        memberRepository.findMemberByKindergartenIdAndBanIsNotNull(kindergartenId)
            .forEach(m -> teacherDetailInfoResponseDtos.add(TeacherDetailInfoResponseDto.toTeacherDetailResponseDto(m)));
        return teacherDetailInfoResponseDtos;
    }

    public List<PendingKidResponseDto> getPendingKids(Long kindergartenId) {
        List<PendingKidResponseDto> pendingKidResponseDtos = new ArrayList<>();
        approvalRepository.findByKindergartenIdAndKidIdIsNotNull(kindergartenId)
            .forEach(m ->pendingKidResponseDtos.add(PendingKidResponseDto.toPendingKidResponseDto(m)));
        return pendingKidResponseDtos;
    }

    public List<PendingKidResponseDto> getAcceptKids(Long kindergartenId) {
        List<PendingKidResponseDto> pendingKidResponseDtos = new ArrayList<>();
        kidRepository.findByKindergartenIdAndIsDeletedFalse(kindergartenId)
            .forEach(m ->pendingKidResponseDtos.add(PendingKidResponseDto.toPendingKidResponseDto(m)));
        return pendingKidResponseDtos;
    }

    @Transactional
    public void acceptTeacherRequestDto(AcceptRequestDto acceptRequestDto) {
        Approval approval = approvalRepository.findByMemberId(acceptRequestDto.getId());
        Long memberId = approval.getMember().getId();
        if (acceptRequestDto.getAccepted()) {
            acceptTeacher(approval);
            sendAcceptMessage(memberId);
        } else {
            memberRepository.updateMemberStatus(memberId, atype.DECLINE);
            sendDeclineMessage(memberId);
        }
        approvalRepository.deleteByMemberId(memberId);
    }

    @Transactional
    public void acceptTeacher(Approval approval) {
        memberRepository.updateMemberBan(approval.getMember().getId(), approval.getBan());
        memberRepository.updateMemberKindergarten(approval.getMember().getId(), approval.getKindergarten());
        memberRepository.updateMemberStatus(approval.getMember().getId(), atype.ACCEPT);
    }

    @Transactional
    public void acceptKidRequestDto(AcceptRequestDto acceptRequestDto) {
        Approval approval = approvalRepository.findByKidId(acceptRequestDto.getId());
        KidMember kidMember = kidMemberRepository.findByKidId(approval.getKid().getId());
        if (acceptRequestDto.getAccepted()) {
            acceptKid(approval, kidMember);
            sendAcceptMessage(kidMember.getMember().getId());
        } else {
            declineKid(acceptRequestDto.getId());
            sendDeclineMessage(kidMember.getMember().getId());
        }
        approvalRepository.deleteByKidId(approval.getKid().getId());
    }

    @Transactional
    public void acceptKid(Approval approval, KidMember kidMember) {
        kidRepository.updateKidBan(approval.getKid().getId(), approval.getBan());
        kidRepository.updateKidKindergarten(approval.getKid().getId(), approval.getKindergarten());
        kidRepository.updateKidStatus(approval.getKid().getId(), atype.ACCEPT);
        memberRepository.updateMemberStatus(kidMember.getMember().getId(), atype.ACCEPT);
        createAttendance(approval.getKid());
    }

    private void createAttendance(Kid kid) {
        attendanceRepository.createNewKid(kid.getId(), LocalDate.now());
    }

    @Transactional
    public void declineKid(Long id) {
        KidMember kidMember = kidMemberRepository.findByKidId(id);
        if (memberRepository.findById(kidMember.getMember().getId()).get().getMemberStatus() != atype.ACCEPT) {
            memberRepository.updateMemberStatus(kidMember.getMember().getId(), atype.DECLINE);
        }
        kidMemberRepository.delete(kidMember);
        kidRepository.deleteKidById(id);
    }

    private void sendAcceptMessage(Long memberId) {
        log.info("[Notification] 승인 알림 전송 시작");
        NotificationUtil.sendMessages(NotificationRequestDto.builder()
            .token(List.of(memberRepository.getNotificationTokenBayMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_TOKEN_MISSING))))
            .notificationContent(NotificationContent.JOIN_APPROVAL)
            .build());
        log.info("[Notification] 승인 알림 전송 완료");
    }

    private void sendDeclineMessage(Long memberId) {
        log.info("[Notification] 거절 알림 전송 시작");
        NotificationUtil.sendMessages(NotificationRequestDto.builder()
            .token(List.of(memberRepository.getNotificationTokenBayMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_TOKEN_MISSING))))
            .notificationContent(NotificationContent.JOIN_DECLINE)
            .build());
        log.info("[Notification] 거절 알림 전송 완료");
    }

    @Transactional
    public void dropTeacher(Long teacherId) {
        memberRepository.updateMemberBan(teacherId, null);
        memberRepository.updateMemberKindergarten(teacherId, null);
        memberRepository.updateMemberStatus(teacherId, atype.DECLINE);
    }

    @Transactional
    public void dropKid(Long kidId) {
        KidMember kidMember = kidMemberRepository.findByKidId(kidId);
        Long parentId = kidMember.getMember().getId();
        kidMemberRepository.delete(kidMember);
        kidRepository.deleteKidById(kidId);
        if (kidMemberRepository.countByMember_Id(parentId) == 0) {
            memberRepository.updateMemberStatus(parentId, atype.DECLINE);
        }
    }

}
