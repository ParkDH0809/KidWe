package yeomeong.common.dto;

import lombok.*;
import yeomeong.common.document.Tag;

@Data
public class TagResponseDto {
    private String id;

    private Long teacherId;

    private String content;

    public TagResponseDto(Tag tag) {
        this.id = tag.getId();

        this.teacherId = tag.getTeacherId();

        this.content = tag.getContent();
    }
}
