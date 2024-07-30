package yeomeong.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.member.Kid;


@Repository
public interface KidReposiory extends JpaRepository<Kid,Long> {
}
