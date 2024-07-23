package yeomeong.common.repository.jpa;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yeomeong.common.dto.homeconsent.HomeconsentByKidAndMonthDto;
import yeomeong.common.entity.jpa.Homeconsent;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HomeconsentRepository {

    private final EntityManager em;

    public void save(Homeconsent homeconsent){
        em.persist(homeconsent);
    }

    public Homeconsent findOne(Long id){
        return em.find(Homeconsent.class, id);
    }

    public List<HomeconsentByKidAndMonthDto> findAll(){
        return em.createQuery("select h from Homeconsent h", HomeconsentByKidAndMonthDto.class)
                .getResultList();
    }

}
