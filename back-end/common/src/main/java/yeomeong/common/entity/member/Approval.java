package yeomeong.common.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Kindergarten;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Approval {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Kid kid;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private Kindergarten kindergarten;

    @OneToOne(fetch = FetchType.LAZY)
    private Ban ban;

}
