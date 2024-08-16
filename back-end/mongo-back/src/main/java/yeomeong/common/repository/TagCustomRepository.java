package yeomeong.common.repository;

import java.util.List;
import yeomeong.common.document.Tag;

public interface TagCustomRepository {
    List<Tag> findTagByTeacherId(Long teacherId);
}
