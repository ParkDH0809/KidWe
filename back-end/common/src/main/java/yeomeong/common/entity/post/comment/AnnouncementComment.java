package yeomeong.common.entity.post.comment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.post.Announcement;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class AnnouncementComment {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Announcement announcement;



    public AnnouncementComment(Comment comment, Announcement announcement) {
        this.comment= comment;
        this.announcement = announcement;
    }


}
