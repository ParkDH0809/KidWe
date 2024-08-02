package yeomeong.common.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yeomeong.common.entity.kindergarten.Ban;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ban_id")
    private Ban ban;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType; //전체공지사항, 반 공지사항

    private String keyword;

    private String content;

    private LocalDate eventDate;
    private LocalTime eventTime;


    public Schedule(Ban ban, String keyword, String content, LocalDate eventDate, LocalTime eventTime) {
        this.ban =ban;
        this.keyword = keyword;
        this.content = content;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    public enum ScheduleType{
        ALLNOTICE, FORBAN
    }
}

