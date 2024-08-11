package yeomeong.common.service;

import com.amazonaws.services.s3.AmazonS3;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yeomeong.common.dto.kid.KidDetailInfoResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;
import yeomeong.common.dto.member.MemberUpdateRequestDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.KidMemberRepository;
import yeomeong.common.repository.MemberRepository;
import yeomeong.common.util.FileUtil;

@Service
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KidMemberRepository kidMemberRepository;
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, KidMemberRepository kidMemberRepository,
        AmazonS3 amazonS3) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.kidMemberRepository = kidMemberRepository;
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
        return MemberProfileResponseDto.toMemberProfileDto(memberRepository.findByEmail(email));
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
        return kidMemberRepository.findKidMemberByMember_Id(memberRepository.findByEmail(email).getId())
            .stream()
            .map(KidDetailInfoResponseDto::toKidDetailInfoDto)
            .collect(Collectors.toList());
    }

}
