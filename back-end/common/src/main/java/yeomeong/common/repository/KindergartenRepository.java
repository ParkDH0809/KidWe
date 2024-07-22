package yeomeong.common.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.kindergarten.Kindergarten;

@Repository
@RequiredArgsConstructor
public class KindergartenRepository {

    private final EntityManager em;

    public void save(Kindergarten kindergarten){
        em.persist(kindergarten);
    }
}
