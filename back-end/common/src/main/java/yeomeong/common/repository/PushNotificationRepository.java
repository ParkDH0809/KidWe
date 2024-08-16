package yeomeong.common.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.member.PushNotification;

@Repository
public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {

    List<PushNotification> findByMember_EmailAndIsDeletedFalse(String email);

    @Modifying
    @Transactional
    @Query("UPDATE PushNotification p SET p.isChecked = true WHERE p.id = :id")
    void updateNotificationChecked(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE PushNotification p SET p.isDeleted = true WHERE p.id = :id")
    void deleteNotificationChecked(Long id);

}
