package yeomeong.common.dto.approval;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.member.Approval;
import yeomeong.common.entity.member.Kid;

@Getter
@Builder
public class PendingKidResponseDto {

    long kidId;
    String name;
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

    public static PendingKidResponseDto toPendingKidResponseDto(Kid kid) {
        return PendingKidResponseDto.builder()
            .kidId(kid.getId())
            .name(kid.getName())
            .banId(kid.getBan().getId())
            .banName(kid.getBan().getName())
            .build();
    }

}
