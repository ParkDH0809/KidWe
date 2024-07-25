package yeomeong.common.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeomeong.common.dto.post.announcement.AnnouncementListDto;
import yeomeong.common.entity.jpa.post.Announcement;

import java.util.List;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

//    // 유치원별 공지사항 조회하기
//    @Query("SELECT new yeomeong.common.dto.post.announcement.AnnouncementListDto(" +
//            "a.post.title, " +
//            "a.member.name, " +
//            "a.member.ban.name," +
//            "a.post.createdDateTime," +
//            "a.commentList.size) " +
//            " FROM Announcement a " +
//            "WHERE a.member.ban.kindergarten.id = :kindergartenId")
//    List<AnnouncementListDto> getAnnouncementByDirector(@Param("kindergartenId") Long kindergartenId);
//
//
//    // 유치원 반 공지사항 조회하기 + 전체 공지사항 조회하기
//    @Query("SELECT new yeomeong.common.dto.post.announcement.AnnouncementListDto(" +
//            "a.post.title," +
//            "a.member.name," +
//            "a.member.ban.name," +
//            "a.post.createdDateTime," +
//            "a.commentList.size)" +
//            "FROM Announcement a WHERE a.member.ban.id = :banId ")
//    List<AnnouncementListDto> getAnnouncementByParent( @Param("banId") Long banId);
//

}


