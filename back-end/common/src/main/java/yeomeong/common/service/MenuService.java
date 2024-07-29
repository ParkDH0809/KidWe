package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.menu.request.MenuByDayRequestDto;
import yeomeong.common.dto.menu.request.MenuCreateDto;
import yeomeong.common.dto.menu.response.MenuByDayResponseDto;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.kindergarten.Menu;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MenuRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final KindergartenRepository kindergartenRepository;


    //일자별 메뉴 가져오기
    public MenuByDayResponseDto getMenuByDay(
            MenuByDayRequestDto menuByDayRequestDto){

        return menuRepository.getMenuByDay(menuByDayRequestDto.getDay(), menuByDayRequestDto.getKindergartenId());

    }

    // 일자별 메뉴 생성하기
    @Transactional
    public void createMenuByDay(Long kindergartenId, MenuCreateDto menuCreateDto) {

        Kindergarten kindergarten = kindergartenRepository.findById(kindergartenId)
                .orElseThrow(()-> new RuntimeException(("Kindergarten not found")));

        Menu menu = new Menu(
                kindergarten,
                menuCreateDto.getLunch(),
                menuCreateDto.getLunchAllergies(),
                menuCreateDto.getSnack(),
                menuCreateDto.getSnackAllergies(),
                menuCreateDto.getDinner(),
                menuCreateDto.getDinnerAllergies(),
                menuCreateDto.getMenuDate());

        menuRepository.save(menu);
    }

    // 일자별 메뉴 수정하기

}
