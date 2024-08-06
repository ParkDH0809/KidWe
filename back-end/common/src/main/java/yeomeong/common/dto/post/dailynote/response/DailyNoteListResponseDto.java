package yeomeong.common.dto.post.dailynote.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.post.DailyNote;

@Getter
@NoArgsConstructor
public class DailyNoteListResponseDto {
    private String yearAndMonth;
    private List<DailyNoteListItemResponseDto> dailyNoteListItemResponseDtos;

    public DailyNoteListResponseDto(String yearAndMonth, List<DailyNote> writedailyNotes, List<DailyNote> recevieddailyNotes) {
        this.yearAndMonth = yearAndMonth;
        this.dailyNoteListItemResponseDtos = new ArrayList<DailyNoteListItemResponseDto>();
        for(DailyNote dailyNote : writedailyNotes) {
            dailyNoteListItemResponseDtos.add(new DailyNoteListItemResponseDto(dailyNote));
        }
        for(DailyNote dailyNote : recevieddailyNotes) {
            dailyNoteListItemResponseDtos.add(new DailyNoteListItemResponseDto(dailyNote));
        }
    }
}
