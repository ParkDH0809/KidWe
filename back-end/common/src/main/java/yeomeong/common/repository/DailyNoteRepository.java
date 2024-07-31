package yeomeong.common.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.repository.querydsl.DailyNoteRepositoryCustom;

@Repository
public interface DailyNoteRepository extends JpaRepository<DailyNote, Long>, DailyNoteRepositoryCustom {
}
