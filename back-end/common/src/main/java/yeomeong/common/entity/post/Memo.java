package yeomeong.common.entity.post;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Teacher;

@Entity
@Setter
@Getter
public class Memo extends Post {


    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private DailyNote dailyNote;

}
