package yeomeong.common.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.Approval;

@Repository
@Transactional
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    List<Approval> findByKindergartenIdAndMemberIdIsNotNull(Long kindergartenId);

    List<Approval> findByKindergartenIdAndKidIdIsNotNull(Long kindergartenId);

    Approval findByMemberId(Long teacherId);

    Approval findByKidId(Long id);

    void deleteByMemberId(Long id);

}
