package yeomeong.common.repository.jpa;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.jpa.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
