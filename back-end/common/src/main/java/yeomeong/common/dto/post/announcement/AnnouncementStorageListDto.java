package yeomeong.common.dto.post.announcement;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AnnouncementStorageListDto {

    private String title;
    private LocalDate storedDate;
    private Long announcementId;
}
