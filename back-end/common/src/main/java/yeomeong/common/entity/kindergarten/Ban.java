package yeomeong.common.entity.kindergarten;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.entity.member.Kid;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ban {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kindergarten kindergarten;

    @JsonIgnore
    @OneToMany(mappedBy = "ban")
    @Builder.Default
    private List<Kid> kids = new ArrayList<>();

}
