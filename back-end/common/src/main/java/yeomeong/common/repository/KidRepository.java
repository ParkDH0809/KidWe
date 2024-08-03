package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.Kid;


@Repository
public interface KidRepository extends JpaRepository<Kid,Long> {
}
