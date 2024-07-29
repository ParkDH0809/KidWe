package yeomeong.common.entity.post;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.member.Kid;

@Getter
@Setter
@Entity
public class KidMemo {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Memo memo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;
}