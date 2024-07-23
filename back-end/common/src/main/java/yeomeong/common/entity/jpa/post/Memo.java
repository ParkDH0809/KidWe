package yeomeong.common.entity.jpa.post;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Memo {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyNote dailyNote;

}
