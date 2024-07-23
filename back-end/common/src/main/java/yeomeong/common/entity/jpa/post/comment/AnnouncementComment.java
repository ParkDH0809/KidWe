package yeomeong.common.entity.jpa.post.comment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.post.Announcement;

@Entity
@Setter
@Getter
public class AnnouncementComment {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Announcement announcement;
}
