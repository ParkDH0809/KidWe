package yeomeong.common.dto;

import lombok.Data;
import yeomeong.common.document.Tag;

@Data
public class TagRequestDto {
    private Long teacherId;

    private String content;

    public Tag toDocument(){
        return Tag.builder()
                .teacherId(this.teacherId)
                .content(this.content)
                .count(1L)
                .build();
    }
}
