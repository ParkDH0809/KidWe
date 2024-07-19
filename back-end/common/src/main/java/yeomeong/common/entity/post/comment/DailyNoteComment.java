package yeomeong.common.entity.post.comment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.post.DailyNote;

import java.util.ArrayList;
import java.util.List;

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
