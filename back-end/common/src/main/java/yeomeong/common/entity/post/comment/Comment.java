package yeomeong.common.entity.post.comment;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Comment {

    private Long writerId;

    private String content;

    private boolean depth;

    private int commentGroup;

    private boolean isDeleted;

    private LocalDateTime createdDateTime;

    public Comment(String content, Long writerId) {
        this.writerId = writerId;
        this.content = content;
        this.createdDateTime = LocalDateTime.now();
        this.commentGroup = 0;
        this.depth = false;
        this.isDeleted = false;
    }

    public Comment(Long commentParentId ,Long writerId, String content ){

        this.writerId = writerId;
        this.content = content;
        this.createdDateTime = LocalDateTime.now();

        this.depth = true;
        this.commentGroup += 1;


    }
}
