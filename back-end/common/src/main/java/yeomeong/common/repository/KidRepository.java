package yeomeong.common.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.Kid;


@Repository
@Transactional
public interface KidRepository extends JpaRepository<Kid,Long> {

    Optional<Kid> findByIdAndIsDeletedFalse(Long id);

    @Modifying
    @Query("UPDATE Kid k SET k.isDeleted = true WHERE k.id = :id")
    void deleteKidById(@Param("id") Long id);

}
