package yeomeong.common.entity.post.comment;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.DailyNote;

@Entity
@Getter
@Setter
public class DailyNoteComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyNote dailyNotes;

    private  Long memberId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyNoteComment parentComment;

    @OneToMany(mappedBy = "parentComment",cascade = CascadeType.ALL)
    private List<DailyNoteComment> replies =new ArrayList<>();

    private LocalDateTime localDateTime;

    private Boolean isDeleted;

}
