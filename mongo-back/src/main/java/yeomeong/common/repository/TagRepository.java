package yeomeong.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import yeomeong.common.document.Tag;

import java.util.List;

public interface TagRepository extends MongoRepository<Tag, String> {
    @Query("{'teacher_id' : $0}")
    List<Tag> findTagByTeacherId(String teacherId);
}
