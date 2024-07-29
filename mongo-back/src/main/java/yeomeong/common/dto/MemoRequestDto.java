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
public class MemoRequestDto {
    private Long teacherId;

    private LocalDateTime createdTime;;
    private LocalDateTime updatedTime;
    private Boolean isDeleted;

    private String lesson;
    private List<Long> kids;
    private List<String> tags;
    private String content;

    public Memo toDocument(){
        Memo memo = Memo.builder()
                .teacherId(this.teacherId)
                .createdTime(this.createdTime==null?LocalDateTime.now():this.createdTime)
                .updatedTime(this.updatedTime==null?this.createdTime:this.updatedTime)
                .lesson(this.lesson==null?"":this.lesson)
                .kids(this.kids==null?new ArrayList<>():this.kids)
                .tags(this.tags==null?new ArrayList<>():this.tags)
                .content(this.content==null?"":this.content)
                .build();
        return memo;
    }
}
