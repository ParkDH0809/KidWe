package yeomeong.common.entity.jpa.post;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.jpa.kindergarten.Ban;
import yeomeong.common.entity.jpa.member.Member;
import yeomeong.common.entity.jpa.post.comment.AnnouncementComment;
import yeomeong.common.entity.jpa.post.comment.Comment;

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

    @OneToMany
    private List<AnnouncementComment> commentList; // ??

    public Announcement(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
