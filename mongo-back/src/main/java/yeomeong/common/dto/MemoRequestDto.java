package yeomeong.common.dto;

import lombok.*;
import yeomeong.common.document.Memo;
import yeomeong.common.document.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemoRequestDto {
    private LocalDateTime updatedTime;

    private String lesson;
    private List<Long> kids;
    private List<TagRequestDto> tagRequestDtos;
    private String content;

    public Memo toDocument(Long teacherId){
        List<Tag> tags = new ArrayList<>();
        if(tagRequestDtos != null && !tagRequestDtos.isEmpty()){
            for(TagRequestDto tagRequestDto : tagRequestDtos){
                tags.add(tagRequestDto.toDocument());
            }
        }

        LocalDateTime now = LocalDateTime.now();
        return Memo.builder()
                .teacherId(teacherId)
                .createdTime(now)
                .updatedTime(this.updatedTime==null?now:this.updatedTime)
                .date(this.updatedTime.toLocalDate().toString())
                .isDeleted(false)
                .lesson(this.lesson==null?"":this.lesson)
                .kids(this.kids==null?new ArrayList<>():this.kids)
                .content(this.content==null?"":this.content)
                .build();
    }
}
