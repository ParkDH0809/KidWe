package yeomeong.common.entity.jpa.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Approve {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private Kid kid;

    @OneToOne
    private Member requester;

    @Enumerated
    private atype approveStatus;

}
