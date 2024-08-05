package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.Approval;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

}
