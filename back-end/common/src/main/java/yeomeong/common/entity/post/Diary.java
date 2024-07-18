package yeomeong.common.entity.post;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Teacher;


@Entity
@Getter
@Setter
public class Diary extends Post {

    private Teacher teacher;
}
