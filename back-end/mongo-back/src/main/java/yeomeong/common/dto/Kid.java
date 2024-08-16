package yeomeong.common.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Kid {

    @Field("kid_id")
    private Long id;
    @Field("kid_name")
    private String name;
}
