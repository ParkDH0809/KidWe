package yeomeong.common.repository.jpa;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.member.Member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long memberId) {
        return em.find(Member.class , memberId);
    }

    public Member findByEmail(String email) {
        try {
            return em.createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            throw e;
        }
    }

}
