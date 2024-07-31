package yeomeong.common.repository.jpa;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
