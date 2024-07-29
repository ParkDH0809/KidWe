package yeomeong.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoDto {
    private String id;
    private Long teacher_id;

    private LocalDateTime createdTime;;
    private LocalDateTime updatedTime;
    private Boolean isDeleted;

    private String lesson;
    private List<Long> kids;
    private List<String> tags;
    private String content;
}
