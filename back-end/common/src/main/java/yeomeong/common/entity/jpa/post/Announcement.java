package yeomeong.common.entity.jpa.post;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.entity.jpa.post.comment.AnnouncementComment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class  Announcement {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Embedded
    private Post post;

    private boolean stored;

    @OneToOne(mappedBy = "announcement",fetch = FetchType.LAZY)
    private Vote vote;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL)
    private List<AnnouncementComment> commentList = new ArrayList<>(); // ??

    public Announcement(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
