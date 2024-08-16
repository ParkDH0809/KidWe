package yeomeong.common.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DailyNoteImage {

    @Id @GeneratedValue
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyNote dailyNote;

    @Builder
    public DailyNoteImage(String imageUrl, DailyNote dailyNote) {
        this.imageUrl = imageUrl;
        this.dailyNote = dailyNote;
    }
}
