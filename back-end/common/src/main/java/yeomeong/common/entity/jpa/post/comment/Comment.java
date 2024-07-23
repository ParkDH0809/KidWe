package yeomeong.common.entity.jpa.post.comment;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Embeddable
public class Comment {

    private String content;

    private boolean depth;

    private int group;

    private boolean isDeleted;

    private Time createdTime;
}
