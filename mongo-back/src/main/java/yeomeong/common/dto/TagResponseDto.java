package yeomeong.common.dto;

import lombok.*;
import yeomeong.common.document.Tag;
import yeomeong.common.document.mtype;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {
    private String id;

    private Long teacherId;

    private String content;
    private mtype morpheme;
    private Long count;

    public TagResponseDto(Tag tag) {
        this.id = tag.getId();

        this.teacherId = tag.getTeacherId();

        this.content = tag.getContent();
        this.morpheme = tag.getMorpheme();
        this.count = tag.getCount();
    }
}
