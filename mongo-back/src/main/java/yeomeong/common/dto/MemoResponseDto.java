package yeomeong.common.dto;

import lombok.*;
import yeomeong.common.document.Memo;
import yeomeong.common.document.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemoResponseDto {
    private String id;

    private Long teacherId;

    private LocalDateTime updatedTime;

    private String lesson;
    private List<Long> kids;
    private List<TagResponseDto> tagResponseDtos;
    private String content;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();

        this.teacherId = memo.getTeacherId();

        this.updatedTime = memo.getUpdatedTime();

        this.lesson = memo.getLesson();
        this.kids = memo.getKids();
        this.tagResponseDtos = new ArrayList<TagResponseDto>();
        if(memo.getTags() != null && !memo.getTags().isEmpty()) {
            for(Tag tag : memo.getTags()) {
                tagResponseDtos.add(new TagResponseDto(tag));
            }
        }
        this.content = memo.getContent();
    }
}
