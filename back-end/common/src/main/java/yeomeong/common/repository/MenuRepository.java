package yeomeong.common.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yeomeong.common.dto.menu.response.MenuByDayResponseDto;
import yeomeong.common.entity.kindergarten.Menu;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {


    @Query("SELECT new yeomeong.common.dto.menu.response.MenuByDayResponseDto(" +
            " m.id, " +
            "   m.lunch, m.lunchAllergies, " +
            "   m.snack, m.snackAllergies, " +
            "   m.dinner, m.dinnerAllergies) " +
            "FROM Menu m " +
            "WHERE m.menuDate = :menuDate " +
            "  AND m.kindergarten.id = :kindergartenId " +
            " AND m.isDeleted = false ")
    MenuByDayResponseDto getMenuByDay(@Param("menuDate") LocalDate menuDate, @Param("kindergartenId") Long kindergartenId);

}

    //일별 급식정보 생성하기

    //일별 급식정보 수정하기

