package yeomeong.common.entity.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushNotification {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.M.d HH:mm")
    private LocalDateTime createDateTime;

    @ColumnDefault("false")
    private Boolean isChecked;

    @ColumnDefault("false")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
