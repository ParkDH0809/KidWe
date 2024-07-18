package yeomeong.common.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Teacher;

import java.util.Date;

@Entity
@Getter
@Setter
public class CalendarNote {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    private String content;

    private String category;

    private Date startDate;

    private Date endDate;

    private Date createdDate;

}
