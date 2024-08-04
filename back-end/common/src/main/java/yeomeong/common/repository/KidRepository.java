package yeomeong.common.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.Kid;


@Repository
public interface KidRepository extends JpaRepository<Kid,Long> {

    Optional<Kid> findByIdAndIsDeletedFalse(Long id);

}
