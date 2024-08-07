package yeomeong.common.dto;

import java.time.format.DateTimeFormatter;
import lombok.*;
import yeomeong.common.document.Memo;
import yeomeong.common.document.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemoResponseDto {

    private String id;

    private Long teacherId;

    private String updatedTime;

    private String lesson;
    private List<Kid> kids;
    private List<TagResponseDto> tags;
    private String content;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();

        this.teacherId = memo.getTeacherId();

        this.updatedTime = memo.getUpdatedTime().format(formatter);

        this.lesson = memo.getLesson();
        this.kids = memo.getKids();
        this.tags = new ArrayList<TagResponseDto>();
        if (memo.getTags() != null && !memo.getTags().isEmpty()) {
            for (Tag tag : memo.getTags()) {
                tags.add(new TagResponseDto(tag));
            }
        }
        this.content = memo.getContent();
    }
}
