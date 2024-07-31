package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.kindergarten.Ban;

@Repository
public interface BanRepository  extends JpaRepository<Ban,Long> {

}
