package yeomeong.common.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.kindergarten.Ban;

@Repository
@RequiredArgsConstructor
public class BanRepository {

    private final EntityManager em;

    public void save(Ban ban){
        em.persist(ban);
    }

    public Ban findOne(Long banId) {
        return em.find(Ban.class,banId);
    }
}
