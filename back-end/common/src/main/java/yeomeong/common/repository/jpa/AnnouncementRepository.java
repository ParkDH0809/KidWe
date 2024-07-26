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

    // 유치원별 전체 공지사항 조회하기
    @Query("SELECT new yeomeong.common.dto.post.announcement.AnnouncementListDto(" +
            "a.post.title, " +
            "a.member.name, " +
            "a.member.ban.name," +
            "a.post.createdDateTime," +
            "size(a.commentList)) " +
            " FROM Announcement a " +
            "WHERE a.member.ban.kindergarten.id = :kindergartenId AND a.member.ban.name = null ")
    List<AnnouncementListDto> getAnnouncementByAll(@Param("kindergartenId") Long kindergartenId);

    // 유치원 반 공지사항 조회하기
    @Query("SELECT new yeomeong.common.dto.post.announcement.AnnouncementListDto(" +
            "a.post.title," +
            "a.member.name," +
            "a.member.ban.name," +
            "a.post.createdDateTime," +
            "size(a.commentList))" +
            "FROM Announcement a WHERE a.member.ban.id = :banId " )
    List<AnnouncementListDto> getAnnouncementByBan( @Param("banId") Long banId);

    //유치원 반 전체 공지사항 가져오기
    @Query("SELECT new yeomeong.common.dto.post.announcement.AnnouncementListDto(" +
            "a.post.title, " +
            "a.member.name, " +
            "a.member.ban.name," +
            "a.post.createdDateTime," +
            "size(a.commentList)) " +
            " FROM Announcement a " +
            "WHERE a.member.ban.kindergarten.id = :kindergartenId AND a.member.ban.name != null ")
    List<AnnouncementListDto> getAnnouncementByAllBan(@Param("kindergartenId") Long kindergartenId);
}


