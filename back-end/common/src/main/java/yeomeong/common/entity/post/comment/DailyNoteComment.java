package yeomeong.common.entity.post.comment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.post.DailyNote;

@Entity
@Getter
@Setter
public class DailyNoteComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyNote dailyNotes;

}
