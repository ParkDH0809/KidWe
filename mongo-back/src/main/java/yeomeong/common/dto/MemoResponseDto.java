package yeomeong.common.dto;

import lombok.*;
import yeomeong.common.document.Memo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoResponseDto {
    private String id;

    private Long teacherId;

    private LocalDateTime updatedTime;
    private String date;

    private String lesson;
    private List<Long> kids;
    private List<String> tags;
    private String content;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();

        this.teacherId = memo.getTeacherId();

        this.updatedTime = memo.getUpdatedTime();
        this.date = memo.getDate();

        this.lesson = memo.getLesson();
        this.kids = memo.getKids();
        this.tags = memo.getTags();
        this.content = memo.getContent();
    }
}
