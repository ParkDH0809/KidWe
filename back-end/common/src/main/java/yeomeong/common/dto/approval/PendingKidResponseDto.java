package yeomeong.common.dto.approval;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Approval;

@Getter
@Builder
public class PendingKidResponseDto {

    long kidId;
    String name;
    String tel;
    long banId;
    String banName;

    public static PendingKidResponseDto toPendingKidResponseDto(Approval approval) {
        return PendingKidResponseDto.builder()
            .kidId(approval.getKid().getId())
            .name(approval.getKid().getName())
            .banId(approval.getBan().getId())
            .banName(approval.getBan().getName())
            .build();
    }

}
