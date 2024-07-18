package yeomeong.common.entity.member;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.Ban;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.Diary;
import yeomeong.common.entity.post.Memo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Teacher extends Member {

    @ManyToOne
    private Ban ban;

    private String picture;

    @OneToMany
    private List<Announcement> announcement = new ArrayList<>();
    @OneToMany
    private List<DailyNote> dailyNotes = new ArrayList<>();
    @OneToMany
    private List<Memo> memos = new ArrayList<>();
    @OneToMany
    private List<Diary> diaries = new ArrayList<>();

}
