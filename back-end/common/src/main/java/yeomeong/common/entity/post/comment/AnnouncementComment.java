package yeomeong.common.entity.post.comment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.post.Announcement;

@Entity
@Setter
@Getter
public class AnnouncementComment extends Comment{

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Announcement announcement;
}
