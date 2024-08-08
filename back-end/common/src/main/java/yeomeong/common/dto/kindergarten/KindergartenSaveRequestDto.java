package yeomeong.common.dto.kindergarten;

import lombok.Builder;
import lombok.Getter;
import yeomeong.common.entity.kindergarten.Kindergarten;

@Builder
@Getter
public class KindergartenSaveRequestDto {

    private Long memberId;
    private String name;
    private String address;
    private String addressDetail;
    private String zipCode;
    private String tel;

    public static Kindergarten toKindergartenEntity(KindergartenSaveRequestDto kindergartenSaveRequestDto) {
        return Kindergarten.builder()
            .name(kindergartenSaveRequestDto.getName())
            .address(kindergartenSaveRequestDto.getAddress())
            .addressDetail(kindergartenSaveRequestDto.addressDetail)
            .zipCode(kindergartenSaveRequestDto.zipCode)
            .tel(kindergartenSaveRequestDto.tel)
            .build();
    }

}