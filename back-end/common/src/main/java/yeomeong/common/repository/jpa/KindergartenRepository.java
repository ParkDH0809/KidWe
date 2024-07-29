package yeomeong.common.repository.jpa;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.kindergarten.Kindergarten;

import java.util.Optional;

@Repository
public interface KindergartenRepository extends JpaRepository<Kindergarten,Long> {


}
