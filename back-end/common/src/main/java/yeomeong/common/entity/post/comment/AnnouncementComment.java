package yeomeong.common.entity.post.comment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private Long memberId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Announcement announcement;

    @ManyToOne(fetch = FetchType.LAZY)
    private AnnouncementComment parentComment;

    @OneToMany(mappedBy = "parentComment",cascade = CascadeType.ALL)
    private List<AnnouncementComment> replies =new ArrayList<>();

    private LocalDateTime localDateTime;


    public AnnouncementComment(Long memberId, String content, Announcement announcement) {
        this.memberId = memberId;
        this.content = content;
        this.announcement = announcement;
        this.localDateTime = LocalDateTime.now();
    }

}
