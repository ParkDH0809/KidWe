package yeomeong.common.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.kindergarten.Ban;

@Repository
@RequiredArgsConstructor
public class BanRepository {

    private final EntityManager em;

    public void save(Ban ban){
        em.persist(ban);
    }
}
