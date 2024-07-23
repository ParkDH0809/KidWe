package yeomeong.common.entity.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.member.Kid;

import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
public class Attendance {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    private String reason;

    private Time inTime;
    private Time outTime;
    private Date date;

}
