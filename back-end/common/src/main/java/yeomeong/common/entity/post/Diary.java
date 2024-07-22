package yeomeong.common.entity.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Diary extends Post {

    @Id @GeneratedValue
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Teacher teacher;
}
