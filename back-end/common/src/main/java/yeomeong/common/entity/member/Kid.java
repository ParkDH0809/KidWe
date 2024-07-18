package yeomeong.common.entity.member;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.Ban;
import yeomeong.common.entity.Bus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Kid {

    @Id
    @GeneratedValue
    @JoinColumn(name = "kid_id")
    private Long id;
    @ManyToOne
    private Ban ban;

    @ManyToOne
    private Bus bus;

    @OneToMany
    private List<kidParent> kidParents = new ArrayList<>();

}
