package yeomeong.common.entity.post;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public abstract class Post {

    @Id @GeneratedValue
    private Long id;

    private Date createdDate;

    private String content;

    private String picture;
}
