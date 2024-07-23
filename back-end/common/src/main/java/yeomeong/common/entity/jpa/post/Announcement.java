package yeomeong.common.entity.jpa.post;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.jpa.kindergarten.Ban;

@Entity
@Setter
@Getter
public class  Announcement {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @Enumerated
    private announcementCategory type; // ??

}
