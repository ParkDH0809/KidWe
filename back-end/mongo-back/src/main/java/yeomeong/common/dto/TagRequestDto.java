package yeomeong.common.dto;

import lombok.Data;
import yeomeong.common.document.Tag;
import yeomeong.common.document.mtype;

@Data
public class TagRequestDto {

    private String id;

    private Long teacherId;

    private String content;

    public Tag toDocument() {
        Tag tag = Tag.builder()
            .id(this.id)
            .teacherId(this.teacherId)
            .content(this.content)
            //content의 morpheme은 서버에서 정해준다
            .morpheme(mtype.SUBJECT)
            .count(1L)
            .build();
        return tag;
    }
}
