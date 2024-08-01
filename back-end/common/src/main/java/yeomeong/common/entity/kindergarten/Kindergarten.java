package yeomeong.common.entity.kindergarten;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kindergarten {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Bus bus;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "kindergarten")
    private List<Ban> bans = new ArrayList<>();

    private String address;

    private String addressDetail;

    private String zipCode;

    private String tel;

    private boolean isVehicle;

    private Date openDate;

}
