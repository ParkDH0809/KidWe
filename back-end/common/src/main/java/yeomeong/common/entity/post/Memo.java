package yeomeong.common.entity.post;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Teacher;

@Entity
@Setter
@Getter
public class Memo extends Post {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyNote dailyNote;

}
