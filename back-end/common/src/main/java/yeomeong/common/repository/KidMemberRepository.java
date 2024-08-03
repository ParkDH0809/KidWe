package yeomeong.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.KidMember;

@Repository
public interface KidMemberRepository extends JpaRepository<KidMember, Long> {

    List<Kid> findKidMemberByMember_Id(Long id);

}