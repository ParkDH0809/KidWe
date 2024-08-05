package yeomeong.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.entity.kindergarten.Ban;

@Repository
@Transactional
public interface BanRepository  extends JpaRepository<Ban,Long> {

    List<Ban> findByKindergarten_Id(Long kindergartenId);

    @Modifying
    @Query("UPDATE Ban b SET b.name = :name WHERE b.id = :id")
    int changeBanName(@Param("id") Long id, @Param("name") String name);

}
