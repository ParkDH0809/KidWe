package yeomeong.common.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Approve {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Kid kid;

    @OneToOne(fetch = FetchType.LAZY)
    private Member requester;

    @Enumerated
    private atype approveStatus;

}
