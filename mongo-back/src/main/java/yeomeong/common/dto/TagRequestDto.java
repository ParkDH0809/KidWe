package yeomeong.common.dto;

import lombok.Data;
import yeomeong.common.document.Tag;
import yeomeong.common.document.mtype;

@Data
public class TagRequestDto {
    private Long teacherId;

    private String content;
    private mtype morpheme;

    public Tag toDocument(){
        Tag tag = Tag.builder()
                .teacherId(this.teacherId)
                .content(this.content)
                .morpheme(this.morpheme)
                .count(1L)
                .build();
        return tag;
    }
}
