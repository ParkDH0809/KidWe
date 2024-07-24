package yeomeong.common.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.member.Kid;

@Repository
@RequiredArgsConstructor
public class KidRepository {

    private final EntityManager em;

    public void save(Kid kid){
        em.persist(kid);
    }
    public Kid findOne(Long kidId){
       return em.find(Kid.class, kidId);
    }
}
