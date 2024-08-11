package yeomeong.common.controller;

import com.amazonaws.services.s3.AmazonS3;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.kid.KidBasicInfoResponseDto;
import yeomeong.common.dto.member.MemberProfileResponseDto;
import yeomeong.common.dto.member.MemberUpdateRequestDto;
import yeomeong.common.service.MemberService;
import yeomeong.common.util.FileUtil;

@Slf4j
@RestController("/members")
@Tag(name = "사용자 API", description = "사용자 관련 API")
public class MemberController {

    final MemberService memberService;
    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public MemberController(MemberService memberService, AmazonS3 s3Client) {
        this.memberService = memberService;
        this.s3Client = s3Client;
    }

    @Operation(summary = "사용자 조회", description = "특정 사용자 정보를 조회합니다.")
    @GetMapping("/profile")
    public ResponseEntity<MemberProfileResponseDto> getMemberProfile(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMemberProfile(authentication.getName()));
    }

    @Operation(summary = "사용자 삭제", description = "특정 사용자를 삭제합니다.")
    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteMemberProfile(Authentication authentication) {
        memberService.deleteMember(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "사용자 정보 수정", description = "특정 사용자 정보를 수정합니다.")
    @PatchMapping("/profile")
    public ResponseEntity<Void> updateMemberProfile(
        @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        String picture = FileUtil.uploadFileToS3(s3Client, bucketName, memberUpdateRequestDto.getPicture());
        memberService.updateMemberProfile(memberUpdateRequestDto, picture);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "사용자 자녀 조회", description = "부모의 자녀 정보를 조회합니다.")
    @GetMapping("/children")
    public ResponseEntity<List<KidBasicInfoResponseDto>> getChildrenByMember(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getChildrenByMember(authentication.getName()));
    }

}