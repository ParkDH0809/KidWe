package yeomeong.common.dto.approval;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Approval;

@Getter
@Builder
public class PendingTeacherResponseDto {

    long memberId;
    String name;
    String tel;
    long banId;
    String banName;

    public static PendingTeacherResponseDto toPendingTeacherResponseDto(Approval approval) {
        return PendingTeacherResponseDto.builder()
            .memberId(approval.getMember().getId())
            .name(approval.getMember().getName())
            .tel(approval.getMember().getTel())
            .banId(approval.getBan().getId())
            .banName(approval.getBan().getName())
            .build();
    }

}
