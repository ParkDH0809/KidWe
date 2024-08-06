package yeomeong.common.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.atype;


@Repository
public interface KidRepository extends JpaRepository<Kid,Long> {

    Optional<Kid> findByIdAndIsDeletedFalse(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Kid k SET k.isDeleted = true WHERE k.id = :id")
    void deleteKidById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Kid k SET k.ban = :ban WHERE k.id = :id")
    void updateKidBan(@Param("id") Long id, @Param("ban") Ban ban);

    @Modifying
    @Transactional
    @Query("UPDATE Kid k SET k.kindergarten = :kindergarten WHERE k.id = :id")
    void updateKidKindergarten(@Param("id") Long id, @Param("kindergarten") Kindergarten kindergarten);

    @Modifying
    @Transactional
    @Query("UPDATE Kid k SET k.kidStatus = :atype WHERE k.id = :id")
    void updateKidStatus(Long id, atype atype);
}
