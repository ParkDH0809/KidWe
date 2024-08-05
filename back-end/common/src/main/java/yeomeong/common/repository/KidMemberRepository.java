package yeomeong.common.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.KidMember;
import yeomeong.common.entity.member.atype;

@Repository
@Transactional
public interface KidMemberRepository extends JpaRepository<KidMember, Long> {

    List<Kid> findKidMemberByMember_Id(Long id);

    @Modifying
    @Query("UPDATE Member m SET m.memberStatus = :memberStatus WHERE m.id = :id")
    int updateMemberStatusById(@Param("id") Long id, @Param("memberStatus") atype memberStatus);

}