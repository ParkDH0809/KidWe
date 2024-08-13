package yeomeong.common.entity.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.entity.member.Member;

import java.util.ArrayList;
import java.util.List;
import yeomeong.common.entity.post.comment.AnnouncementComment;
import yeomeong.common.entity.post.comment.DailyNoteComment;

@Entity

@Getter
@NoArgsConstructor
public class DailyNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;
    private String content;
    @OneToMany(mappedBy = "dailyNote", cascade = CascadeType.ALL)
    private List<DailyNoteImage> images;
    private LocalDateTime sendTime;

    @OneToMany(mappedBy = "dailyNote", cascade = CascadeType.ALL)
    private List<DailyNoteComment> comments;

    private Boolean isDeleted;

    @Builder
    public DailyNote(Kid kid,
                     Member writer,
                     String content,
                     LocalDateTime sendTime){
        this.kid = kid;
        this.writer = writer;
        this.content = content;
        this.images = new ArrayList<>();
        this.sendTime = sendTime;
        this.comments = new ArrayList<>();
        this.isDeleted = false;
    }

    public void setNewContent(String content){
        this.content = content;
    }

    public void setNewSendTime(LocalDateTime sendTime){
        this.sendTime = sendTime;
    }

    public void delete(){
        this.isDeleted = true;
    }
}