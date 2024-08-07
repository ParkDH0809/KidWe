package yeomeong.common.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import yeomeong.common.document.Memo;
import yeomeong.common.document.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MemoRequestDto {

    private String updatedTime;

    private String lesson;
    private List<Kid> kids;
    private List<TagRequestDto> tagRequestDtos;
    private String content;


    public Memo toDocument(Long teacherId) {
        List<Tag> tags = new ArrayList<>();
        if (tagRequestDtos != null && !tagRequestDtos.isEmpty()) {
            for (TagRequestDto tagRequestDto : tagRequestDtos) {
                tags.add(tagRequestDto.toDocument());
            }
        }

        return Memo.builder()
            .teacherId(teacherId)
            .updatedTime(this.updatedTime)
            .lesson(this.lesson == null ? "" : this.lesson)
            .kids(this.kids == null ? new ArrayList<>() : this.kids)
            .content(this.content == null ? "" : this.content)
            .build();
    }
}
