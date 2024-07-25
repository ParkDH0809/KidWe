package yeomeong.common.dto.memo;

import yeomeong.common.entity.jpa.member.Kid;
import yeomeong.common.entity.mongo.MongoTag;

import java.util.ArrayList;
import java.util.List;

public class MongoMemoDto {
    private Long id;

    private Integer createdYear;
    private Integer createdMonth;
    private Integer createdDay;
    private Integer createdHour;
    private Integer createdMinute;
    private Integer createdSecond;

    private Integer memoYear;
    private Integer memoMonth;
    private Integer memoDay;
    private Integer memoHour;
    private Integer memoMinute;
    private Integer memoSecond;

    private List<Kid> kids = new ArrayList<>();
    private List<MongoTag> tags = new ArrayList<>();

    private String content;
}
