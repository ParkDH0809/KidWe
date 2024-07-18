package yeomeong.common.entity.member;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class Member {

    @Id @GeneratedValue
    @JoinColumn(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String password;

}
