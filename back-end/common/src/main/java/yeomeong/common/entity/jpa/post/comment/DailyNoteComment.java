package yeomeong.common.entity.jpa.post.comment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.post.DailyNote;

@Entity
@Getter
@Setter
public class DailyNoteComment {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyNote dailyNotes;

}
