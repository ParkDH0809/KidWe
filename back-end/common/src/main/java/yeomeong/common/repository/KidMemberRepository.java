package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.KidMember;

@Repository
public interface KidMemberRepository extends JpaRepository<KidMember, Long> {

}