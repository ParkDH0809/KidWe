package yeomeong.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import yeomeong.common.entity.kindergarten.Kindergarten;

@Repository
public interface KindergartenRepository extends JpaRepository<Kindergarten,Long>, QuerydslPredicateExecutor<Kindergarten> {

}
