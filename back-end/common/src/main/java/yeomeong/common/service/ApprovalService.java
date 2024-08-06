package yeomeong.common.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.approval.AcceptRequestDto;
import yeomeong.common.dto.approval.ApprovalRequestDto;
import yeomeong.common.dto.approval.PendingKidResponseDto;
import yeomeong.common.dto.approval.PendingTeacherResponseDto;
import yeomeong.common.dto.approval.KidJoinKindergartenRequestDto;
import yeomeong.common.dto.approval.TeacherJoinKindergartenRequestDto;
import yeomeong.common.entity.member.Approval;
import yeomeong.common.entity.member.KidMember;
import yeomeong.common.entity.member.atype;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.ApprovalRepository;
import yeomeong.common.repository.BanRepository;
import yeomeong.common.repository.KidMemberRepository;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MemberRepository;

@Service
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final MemberRepository memberRepository;
    private final BanRepository banRepository;
    private final KidRepository kidRepository;
    private final KindergartenRepository kindergartenRepository;
    private final KidMemberRepository kidMemberRepository;

    public ApprovalService(ApprovalRepository approvalRepository, MemberRepository memberRepository, BanRepository banRepository,
        KidRepository kidRepository, KindergartenRepository kindergartenRepository, KidMemberRepository kidMemberRepository) {
        this.approvalRepository = approvalRepository;
        this.memberRepository = memberRepository;
        this.banRepository = banRepository;
        this.kidRepository = kidRepository;
        this.kindergartenRepository = kindergartenRepository;
        this.kidMemberRepository = kidMemberRepository;
    }

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
    public void applyForKindergartenByGuardian(KidJoinKindergartenRequestDto kidJoinKindergartenRequestDto) {
        long kidId = kidRepository.save(KidJoinKindergartenRequestDto.toKidEntity(kidJoinKindergartenRequestDto)).getId();
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

    public List<PendingTeacherResponseDto> getPendingTeachers(Long kindergartenId) {
        List<PendingTeacherResponseDto> pendingTeacherResponseDtos = new ArrayList<>();
        approvalRepository.findByKindergartenIdAndMemberIdIsNotNull(kindergartenId)
            .forEach(m ->pendingTeacherResponseDtos.add(PendingTeacherResponseDto.toPendingTeacherResponseDto(m)));
        return pendingTeacherResponseDtos;
    }

    public List<PendingKidResponseDto> getPendingKids(Long kindergartenId) {
        List<PendingKidResponseDto> pendingKidResponseDtos = new ArrayList<>();
        approvalRepository.findByKindergartenIdAndKidIdIsNotNull(kindergartenId)
            .forEach(m ->pendingKidResponseDtos.add(PendingKidResponseDto.toPendingKidResponseDto(m)));
        return pendingKidResponseDtos;
    }

    public void acceptTeacherRequestDto(AcceptRequestDto acceptRequestDto) {
        Approval approval = approvalRepository.findByMemberId(acceptRequestDto.getId());
        if (acceptRequestDto.getAccepted()) {
            acceptTeacher(approval);
        } else {
            declineTeacher(acceptRequestDto.getId());
        }
        approvalRepository.delete(approval);
    }

    public void acceptTeacher(Approval approval) {
        memberRepository.updateMemberBan(approval.getMember().getId(), approval.getBan());
        memberRepository.updateMemberKindergarten(approval.getMember().getId(), approval.getKindergarten());
        memberRepository.updateMemberStatus(approval.getMember().getId(), atype.ACCEPT);
    }

    public void declineTeacher(Long memberId) {
        memberRepository.updateMemberStatus(memberId, atype.DECLINE);
    }

    public void acceptKidRequestDto(AcceptRequestDto acceptRequestDto) {
        Approval approval = approvalRepository.findByKidId(acceptRequestDto.getId());
        if (acceptRequestDto.getAccepted()) {
            acceptKid(approval);
        } else {
            declineKid(acceptRequestDto.getId());
        }
        approvalRepository.delete(approval);
    }

    private void acceptKid(Approval approval) {
        KidMember kidMember = kidMemberRepository.findByKidId(approval.getKid().getId());
        kidRepository.updateKidBan(approval.getKid().getId(), approval.getBan());
        kidRepository.updateKidKindergarten(approval.getKid().getId(), approval.getKindergarten());
        kidRepository.updateKidStatus(approval.getKid().getId(), atype.ACCEPT);
        memberRepository.updateMemberStatus(kidMember.getMember().getId(), atype.ACCEPT);
    }

    private void declineKid(Long id) {
        KidMember kidMember = kidMemberRepository.findByKidId(id);
        if (memberRepository.findById(kidMember.getMember().getId()).get().getMemberStatus() != atype.ACCEPT) {
            memberRepository.updateMemberStatus(kidMember.getMember().getId(), atype.DECLINE);
        }
        kidMemberRepository.delete(kidMember);
        kidRepository.deleteKidById(id);
    }

}
