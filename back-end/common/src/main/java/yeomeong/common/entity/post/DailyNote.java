package yeomeong.common.entity.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.Teacher;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class DailyNote extends Post {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "dailyNote")
    private List<Memo> memos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "kid_id")
    private Long kidId;

    @JoinColumn(name = "member_id")
    private Long receiverId;

    @JoinColumn(name = "member_id")
    private Long writerId;


}
