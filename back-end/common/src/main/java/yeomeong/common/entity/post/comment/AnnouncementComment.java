package yeomeong.common.entity.post.comment;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.Announcement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class AnnouncementComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Announcement announcement;

    @ManyToOne(fetch = FetchType.LAZY)
    private AnnouncementComment parentComment;

    @OneToMany(mappedBy = "parentComment",cascade = CascadeType.ALL)
    private List<AnnouncementComment> replies =new ArrayList<>();

    private LocalDateTime localDateTime;

    @ColumnDefault("false")
    private boolean isDeleted;


    public AnnouncementComment(Member member, String content, Announcement announcement) {
        this.member= member;
        this.content = content;
        this.announcement = announcement;
        this.localDateTime = LocalDateTime.now();
    }

}
