package yeomeong.common.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.atype;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    List<Member> findMemberByBanId(Long banId);

    List<Member> findMemberByKindergartenIdAndBanIsNotNull(Long kindergartenId);

    @Modifying
    @Query("UPDATE Member m SET m.isDeleted = true WHERE m.email = :email")
    void deleteMemberByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.ban = :ban WHERE m.id = :id")
    void updateMemberBan(@Param("id") Long id, @Param("ban") Ban ban);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.kindergarten = :kindergarten WHERE m.id = :id")
    void updateMemberKindergarten(@Param("id") Long id, @Param("kindergarten") Kindergarten kindergarten);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.memberStatus = :atype WHERE m.id = :id")
    void updateMemberStatus(@Param("id") Long id, @Param("atype") atype atype);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.notificationToken = :token WHERE m.id = :id")
    void updateNotificationToken(@Param("id") Long id, @Param("token") String token);

    @Query("SELECT m.notificationToken FROM Member m WHERE m.id = :id")
    Optional<String> getNotificationTokenBayMemberId(Long id);
}