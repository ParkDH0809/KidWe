package yeomeong.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.kindergarten.Ban;

@Repository
public interface BanRepository  extends JpaRepository<Ban,Long> {

}
