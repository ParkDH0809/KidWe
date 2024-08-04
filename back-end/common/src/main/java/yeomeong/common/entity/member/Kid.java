package yeomeong.common.entity.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import yeomeong.common.dto.kid.KidUpdateInfoDto;
import yeomeong.common.entity.kindergarten.Ban;
import yeomeong.common.entity.kindergarten.Bus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import yeomeong.common.entity.kindergarten.Kindergarten;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthday;

    private LocalDate startAttendanceDate;

    private String picture;

    private String allergies;

    @Enumerated(EnumType.STRING)
    private gtype gender; //MALE , FEMALE

    private boolean isTake;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kindergarten kindergarten;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bus bus;

    @JsonIgnore
    @OneToMany(mappedBy = "kid")
    @Builder.Default
    private List<KidMember> kidMembers = new ArrayList<>();

    @ColumnDefault("false")
    private Boolean isDeleted;

    public Kid updateFromDto(KidUpdateInfoDto dto) {
        return Kid.builder()
            .id(this.id)
            .name(dto.getName() != null ? dto.getName() : this.name)
            .birthday(dto.getBirthday() != null ? dto.getBirthday() : this.birthday)
            .startAttendanceDate(this.startAttendanceDate)
            .gender(dto.getGender() != null ? dto.getGender() : this.gender)
            .allergies(dto.getAllergies() != null ? listToString(dto.getAllergies()) : this.allergies)
            .picture(dto.getPicture() != null ? dto.getPicture() : this.picture)
            .isTake(this.isTake)
            .kindergarten(this.kindergarten)
            .ban(this.ban)
            .bus(this.bus)
            .kidMembers(this.kidMembers)
            .isDeleted(this.isDeleted)
            .build();
    }

    private static String listToString(List<String> list) {
        return String.join(",", list);
    }

}
