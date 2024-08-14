package yeomeong.common.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import yeomeong.common.document.Tag;

@Repository
public class TagCustomRepositoryImpl implements TagCustomRepository{
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Tag> findTagByTeacherId(Long teacherId) {
        Aggregation agg = Aggregation.newAggregation(
            // 조건문
            Aggregation.match(Criteria.where("teacher_id").is(teacherId)),
            // 정렬문
            Aggregation.sort(Sort.by(Direction.DESC, "count").and(Sort.by(Direction.ASC, "is_first")))
        );

        AggregationResults<Tag> results = mongoTemplate.aggregate(agg, "tag", Tag.class);
        return results.getMappedResults();
    }
}