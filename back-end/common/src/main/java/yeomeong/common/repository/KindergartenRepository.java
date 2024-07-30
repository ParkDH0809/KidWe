package yeomeong.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.kindergarten.Kindergarten;

import java.util.Optional;

@Repository
public interface KindergartenRepository extends JpaRepository<Kindergarten,Long> {

    @Override
    Optional<Kindergarten> findById(Long id);


}
