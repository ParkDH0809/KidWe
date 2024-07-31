package yeomeong.common.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.dto.member.MemberSaveRequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    private MemberSaveRequestDto memberSaveRequestDto;
    private KindergartenSaveRequestDto kindergartenSaveRequestDto;

}