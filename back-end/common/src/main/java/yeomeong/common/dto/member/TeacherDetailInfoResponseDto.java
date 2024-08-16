package yeomeong.common.dto.member;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Approval;
import yeomeong.common.entity.member.Member;

@Getter
@Builder
public class TeacherDetailInfoResponseDto {

    long memberId;
    String name;
    String tel;
    long banId;
    String banName;

    public static TeacherDetailInfoResponseDto toTeacherDetailResponseDto(Approval approval) {
        return TeacherDetailInfoResponseDto.builder()
            .memberId(approval.getMember().getId())
            .name(approval.getMember().getName())
            .tel(approval.getMember().getTel())
            .banId(approval.getBan().getId())
            .banName(approval.getBan().getName())
            .build();
    }

    public static TeacherDetailInfoResponseDto toTeacherDetailResponseDto(Member member) {
        return TeacherDetailInfoResponseDto.builder()
            .memberId(member.getId())
            .name(member.getName())
            .tel(member.getTel())
            .banId(member.getBan().getId())
            .banName(member.getBan().getName())
            .build();
    }

}
