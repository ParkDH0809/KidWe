package yeomeong.common.repository.jpa;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.kindergarten.Kindergarten;

@Repository
@RequiredArgsConstructor
public class KindergartenRepository {

    private final EntityManager em;

    public void save(Kindergarten kindergarten){
        em.persist(kindergarten);
    }
}
