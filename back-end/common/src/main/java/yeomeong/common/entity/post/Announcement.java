package yeomeong.common.entity.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.comment.AnnouncementComment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class  Announcement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @Embedded
    private Post post;

    @Column(name = "is_stored")
    private boolean stored;

    @OneToOne(mappedBy = "announcement",fetch = FetchType.LAZY)
    private Vote vote;

    @OneToMany(mappedBy = "announcement")
    private List<AnnouncementImage> announcementImages = new ArrayList<>();

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL)
    private List<AnnouncementComment> commentList = new ArrayList<>(); // ??

    @ColumnDefault("false")
    private boolean isDeleted;

    public Announcement( Post post ,Member member) {
        this.post = post;
        this.member = member;
    }

}
