package yeomeong.common.entity.jpa.post;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Announcement announcement;

    @ElementCollection
    private Map<String, Integer> items = new HashMap<>();


    public void addItem(String item){
        items.put(item, 0);
    }

    public void doVote(String item){
        items.put(item, items.getOrDefault(item,0)+1);
    }

    public Vote(String title, LocalDate startDate, LocalDate endDate, Map<String,Integer> items){
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.items = items;
    }
}

