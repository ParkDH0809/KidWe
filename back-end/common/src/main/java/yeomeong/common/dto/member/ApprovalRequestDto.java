package yeomeong.common.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.member.Approval;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;

@Getter
@Builder
@AllArgsConstructor
public class ApprovalRequestDto {

    Kid kid;
    Member member;
    Ban ban;
    Kindergarten kindergarten;

    public ApprovalRequestDto(Kid kid, Ban ban, Kindergarten kindergarten) {
        this.kid = kid;
        this.ban = ban;
        this.kindergarten = kindergarten;
    }

    public ApprovalRequestDto(Member member, Ban ban, Kindergarten kindergarten) {
        this.member = member;
        this.ban = ban;
        this.kindergarten = kindergarten;
    }

    public static Approval toApprovalEntity(ApprovalRequestDto approvalRequestDto) {
        return Approval
            .builder()
            .kid(approvalRequestDto.getKid())
            .member(approvalRequestDto.getMember())
            .ban(approvalRequestDto.getBan())
            .kindergarten(approvalRequestDto.getKindergarten())
            .build();
    }

}
