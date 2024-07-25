package yeomeong.common.entity.jpa.post.comment;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Embeddable
public class Comment {

    private Long writerId;

    private String content;

    private boolean depth;

    private int group;

    private boolean isDeleted;

    private LocalDateTime createdDateTime;
}
