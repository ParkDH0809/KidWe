package yeomeong.common.entity.post;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Embeddable
public class Post {

    private LocalDateTime createdDateTime;

    private String title;

    private String content;

    private String picture;
}
