package yeomeong.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import yeomeong.common.document.Tag;

import java.util.List;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

    @Query("{'teacher_id' : ?0}")
    List<Tag> findTagByTeacherId(Long teacherId);

    @Query("{'teacher_id' : ?0, 'contet' :  ?1}")
    Tag findTagByTeacherIdAndContet(Long teacherId, String contet);
}
