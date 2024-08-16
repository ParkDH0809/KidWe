package yeomeong.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import yeomeong.common.document.Tag;

@Getter
@NoArgsConstructor
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
