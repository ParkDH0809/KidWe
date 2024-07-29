package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.entity.jpa.LeaveConsent;
import yeomeong.common.entity.jpa.kindergarten.Ban;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.entity.jpa.member.rtype;
import yeomeong.common.repository.jpa.BanRepository;
import yeomeong.common.repository.jpa.LeaveConsentRepository;
import yeomeong.common.repository.jpa.MemberRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveConsentService {

    private final LeaveConsentRepository leaveConsentRepository;
    private final MemberRepository memberRepository;
    private final BanRepository banRepository;

    //반 별로 리스트 (학부모, 선생님일 때 나누어 구현)

    /*public List<LeaveConsentByMonthAndBanListDto> getLeaveConsentByMonthAndBanList(Long memberId, Long banId, int year, int month) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다"));


        List<LeaveConsentByMonthAndBanListDto> allByKidBanIdAndYearAndMonth = new ArrayList<>();
        List<LeaveConsent> leaveConsentList = new ArrayList<>();


        if(member.getRole() == rtype.DIRECTOR) {//원장님일때


        }

    }
    */

}
