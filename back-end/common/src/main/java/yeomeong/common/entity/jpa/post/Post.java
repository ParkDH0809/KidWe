package yeomeong.common.entity.jpa.post;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Embeddable
public class Post {

    private Date createdDate;

    private String content;

    private boolean isStored;

    private String picture;
}
