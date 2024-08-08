package yeomeong.common.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.dto.leaveconsent.LeaveConsentDetailDto;
import yeomeong.common.dto.leaveconsent.QLeaveConsentByMonthAndBanListDto;
import yeomeong.common.entity.LeaveConsent;


import java.util.List;

import static yeomeong.common.entity.QLeaveConsent.leaveConsent;

import static yeomeong.common.entity.kindergarten.QBan.ban;
import static yeomeong.common.entity.member.QKid.kid;

@Transactional
@Repository
@RequiredArgsConstructor
public class LeaveConsentRepositoryImpl implements LeaveConsentRepository {


    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    public void save(LeaveConsent leaveConsent){
        em.persist(leaveConsent);
    }

    @Override
    public List<LeaveConsentByMonthAndBanListDto> findAllByBan_IdAndYearAndMonth(Long banId, int year, int month) {


        return jpaQueryFactory
                .select(new QLeaveConsentByMonthAndBanListDto(
                        leaveConsent.id,
                        kid.name,
                        ban.name,
                        leaveConsent.leaveDate
                )).from(leaveConsent)
                .join(leaveConsent.kid, kid)
                .on(leaveConsent.kid.id.eq(kid.id)) // 조인 조건 추가
                .join(kid.ban, ban) // Ban과의 조인
                .on(kid.ban.id.eq(ban.id)) // 추가적인 조인 조건
                .where(leaveConsent.kid.ban.id.eq(banId)
                        .and(leaveConsent.leaveDate.year().intValue().eq(year)
                                .and(leaveConsent.leaveDate.month().intValue().eq(month))
                                .and(leaveConsent.isDeleted.isFalse())))
                .fetch();
    }
    @Override
    public List<LeaveConsentByMonthAndBanListDto> findAllByKid_IdAndYearAndMonth(Long kidId, int year, int month) {

        return jpaQueryFactory.select(new QLeaveConsentByMonthAndBanListDto(
                leaveConsent.id,
                kid.name,
                ban.name,
                leaveConsent.leaveDate))
                .from(leaveConsent)
                .join(leaveConsent.kid, kid)
                .on(leaveConsent.kid.id.eq(kid.id))
                .join(kid.ban, ban) // Ban과의 조인
                .on(kid.ban.id.eq(ban.id)) // 추가적인 조인 조건
                .where(leaveConsent.kid.id.eq(kidId)
                        .and(leaveConsent.leaveDate.year().intValue().eq(year)
                                .and(leaveConsent.leaveDate.month().intValue().eq(month))
                                .and(leaveConsent.isDeleted.isFalse())))
                .fetch();

    }


    @Override
    public LeaveConsent findById(Long leaveConsentId) {
        return em.find(LeaveConsent.class, leaveConsentId);
    }


    @Override
    public LeaveConsentDetailDto getLeaveConsentDetail(Long leaveConsentId) {


        LeaveConsent leaveConsent = em.find(LeaveConsent.class, leaveConsentId);

        return new LeaveConsentDetailDto(
                leaveConsent.getLeaveDate(),
                leaveConsent.getLeaveTime(),
                leaveConsent.getLeaveMethod(),
                leaveConsent.getGuardianRelationship(),
                leaveConsent.getGuardianContact(),
                leaveConsent.getEmergencyRelationship(),
                leaveConsent.getEmergencyContact(),
                leaveConsent.getCreatedDate(), // 수정 고려
                leaveConsent.getGuardianName(),
                leaveConsent.getSignUrl()
        );
    }
}
