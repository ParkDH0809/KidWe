package yeomeong.common.entity.kindergarten;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Kid;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Ban {

    @Id @GeneratedValue
    @JoinColumn(name = "ban_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private KinderGarten kinderGarten;

    @OneToMany(mappedBy = "ban")
    private List<Kid> kids = new ArrayList<>();

}
