package yeomeong.common.entity.post;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
public class Post {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.M.d HH:mm")
    private LocalDateTime createdDateTime;

    private String title;

    private String content;

    public Post() {

    }
}
