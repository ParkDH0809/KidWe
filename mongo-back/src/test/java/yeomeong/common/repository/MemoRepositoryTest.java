package yeomeong.common.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import yeomeong.common.document.Memo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository;

    @BeforeEach
    public void setUp() {
        memoRepository.deleteAll();

        Memo memo1 = new Memo("1", 1L, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1), false, "Lesson 1", Arrays.asList(1L, 2L), Arrays.asList("tag1"), "Content 1");
        Memo memo2 = new Memo("2", 1L, LocalDateTime.now(), LocalDateTime.now(), false, "Lesson 2", Arrays.asList(3L), Arrays.asList("tag2"), "Content 2");
        Memo memo3 = new Memo("3", 2L, LocalDateTime.now(), LocalDateTime.now(), false, "Lesson 3", Arrays.asList(1L), Arrays.asList("tag3"), "Content 3");

        memoRepository.saveAll(Arrays.asList(memo1, memo2, memo3));
    }

    @Test
    public void testFindByTeacherIdAndUpdatedTimeBetweenDates() {
        LocalDate date = LocalDate.now();
        List<Memo> memos = memoRepository.findByTeacherIdAndUpdatedTimeBetweenDates(1L, date);

        assertThat(memos).hasSize(2);
        assertThat(memos.get(0).getTeacher_id()).isEqualTo(1L);
    }

    @Test
    public void testFindByTeacherIdAndUpdatedTimeBetweenDatesAndKidId() {
        LocalDate date = LocalDate.now();

        List<Memo> memos = memoRepository.findByTeacherIdAndUpdatedTimeBetweenDatesAndKidId(1L, date, 1L);

        assertThat(memos).hasSize(1);
        assertThat(memos.get(0).getKids()).contains(1L);
    }
}
