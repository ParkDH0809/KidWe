package yeomeong.common.entity.post;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AnnouncementImage {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Announcement announcement;

    public AnnouncementImage(String imageUrl, Announcement announcement) {
        this.imageUrl = imageUrl;
        this.announcement = announcement;
    }
}
