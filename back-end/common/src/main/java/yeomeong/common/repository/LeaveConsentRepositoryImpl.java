package yeomeong.common.repository.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.leaveconsent.LeaveConsentByMonthAndBanListDto;
import yeomeong.common.dto.leaveconsent.QLeaveConsentByMonthAndBanListDto;
import yeomeong.common.entity.jpa.LeaveConsent;



import java.util.List;

import static yeomeong.common.entity.jpa.kindergarten.QBan.ban;
import static yeomeong.common.entity.jpa.QLeaveConsent.leaveConsent;
import static yeomeong.common.entity.jpa.member.QKid.kid;

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
                .join(kid)
                .join(ban)
                .where(leaveConsent.kid.ban.id.eq(banId)
                        .and(leaveConsent.leaveDate.year().intValue().eq(year)
                                .and(leaveConsent.leaveDate.month().intValue().eq(month))))
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
                .join(kid)
                .join(ban)
                .where(leaveConsent.kid.id.eq(kidId)
                        .and(leaveConsent.leaveDate.year().intValue().eq(year)
                                .and(leaveConsent.leaveDate.month().intValue().eq(month))))
                .fetch();

    }

    @Override
    public void remove(LeaveConsent leaveConsent) {



    }

    @Override
    public LeaveConsent findById(Long leaveConsentId) {
        return em.find(LeaveConsent.class, leaveConsentId);
    }
}
