package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.AnnouncementImage;

@Repository
public interface AnnouncementImageRepository extends JpaRepository<AnnouncementImage,Long > {
}
