package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yeomeong.common.entity.member.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

//    Optional<Notification> findByMember(Member member);

}