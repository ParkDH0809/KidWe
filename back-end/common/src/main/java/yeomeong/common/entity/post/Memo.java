package yeomeong.common.entity.post;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Memo extends Post {
    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyNote dailyNote;
}
