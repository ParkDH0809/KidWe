package yeomeong.common.dto;

import lombok.Getter;
import org.springframework.lang.Nullable;
import yeomeong.common.document.Tag;
import yeomeong.common.document.mtype;

@Getter
public class TagRequestDto {
    @Nullable
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
