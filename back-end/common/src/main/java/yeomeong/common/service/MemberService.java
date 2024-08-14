package yeomeong.common.service;

import com.amazonaws.services.s3.AmazonS3;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.kid.KidDetailInfoResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;
import yeomeong.common.dto.member.MemberUpdateRequestDto;
import yeomeong.common.dto.notification.NotificationRequestDto;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.KidMember;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.atype;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.KidMemberRepository;
import yeomeong.common.repository.MemberRepository;
import yeomeong.common.util.FileUtil;
import yeomeong.common.util.NotificationUtil;

@Service
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KidMemberRepository kidMemberRepository;
    private final NotificationUtil notificationUtil;
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, KidMemberRepository kidMemberRepository,
            NotificationUtil notificationUtil, AmazonS3 amazonS3) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.kidMemberRepository = kidMemberRepository;
        this.notificationUtil = notificationUtil;
        this.amazonS3 = amazonS3;
    }

    @Transactional
    public void joinMember(MemberSaveRequestDto memberSaveRequestDto, MultipartFile picture) {
        if (memberRepository.findByEmail(memberSaveRequestDto.getEmail()) != null) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_EMAIL);
        }

        String pictureName = null;
        if (picture != null && !picture.isEmpty()) {
            pictureName = FileUtil.uploadFileToS3(amazonS3, bucketName, picture);
        }
        Member member = MemberSaveRequestDto.toMemberEntity(memberSaveRequestDto, pictureName);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public MemberProfileResponseDto getMemberProfile(String email) {
        Member member = getMemberByEmail(email);
        MemberProfileResponseDto dto = MemberProfileResponseDto.toMemberProfileDto(member);
        if (member.getMemberStatus() == atype.ACCEPT) {
            initKindergartenName(member, dto);
        }
        return dto;
    }

    private void initKindergartenName(Member member, MemberProfileResponseDto dto) {
        if (member.getRole() == rtype.ROLE_GUARDIAN) {
            List<KidMember> kidMembers = member.getKidMember();
            for (KidMember kidMember : kidMembers) {
                Kid kid = kidMember.getKid();
                dto.setKindergartenName(kid.getKindergarten().getName());
            }
        } else {
            dto.setKindergartenName(member.getKindergarten().getName());
        }
    }

    public void updateMemberProfile(MemberUpdateRequestDto memberUpdateRequestDto, MultipartFile picture) {
        String pictureName = null;
        if (picture != null && !picture.isEmpty()) {
            pictureName = FileUtil.uploadFileToS3(amazonS3, bucketName, picture);
        }
        Member member = memberRepository.findById(memberUpdateRequestDto.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT_VALUE));
        if (memberUpdateRequestDto.getPassword() != null) {
            member.setPassword(passwordEncoder.encode(memberUpdateRequestDto.getPassword()));
        }
        member.updateFromDto(memberUpdateRequestDto, pictureName);
    }

    public void deleteMember(String email) {
        memberRepository.deleteMemberByEmail(email);
    }

    public List<KidDetailInfoResponseDto> getChildrenByMember(String email) {
        try {
            return kidMemberRepository.findKidMemberByMember_Id(memberRepository.findByEmail(email).getId())
                    .stream()
                    .map(KidDetailInfoResponseDto::toKidDetailInfoDto)
                    .collect(Collectors.toList());
        } catch (JpaSystemException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_KIDS);
        }
    }

    public void testNotification(String email) {
        try {
            Member member = memberRepository.findByEmail(email);
            notificationUtil.sendMessages(NotificationRequestDto.builder()
                    .token(List.of(memberRepository.getNotificationTokenByMemberId(member.getId())
                            .orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_TOKEN_MISSING))))
                    .email(List.of(member.getEmail()))
                    .notificationContent(NotificationContent.TEST)
                    .build());
        } catch (CustomException e) {
            log.info("[Notification] 알림 토큰이 없는 회원입니다.");
        }
    }

}
