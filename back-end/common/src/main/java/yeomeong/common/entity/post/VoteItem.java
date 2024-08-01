package yeomeong.common.entity.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VoteItem {

    @Id @GeneratedValue
    private Long id;

    private String itemName;

    private int value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Vote vote;

    public VoteItem(String itemName, int value) {
        this.itemName = itemName;
        this.value = value;
    }
}
