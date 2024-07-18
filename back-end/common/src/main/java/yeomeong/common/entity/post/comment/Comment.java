package yeomeong.common.entity.post.comment;


import lombok.Getter;

import java.sql.Time;

@Getter
public abstract class Comment {

    private String content;

    private boolean depth;

    private int group;

    private boolean isDeleted;

    private Time createdTime;
}
