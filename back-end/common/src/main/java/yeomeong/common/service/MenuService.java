package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.menu.request.MenuByDayRequestDto;
import yeomeong.common.dto.menu.request.MenuCreateDto;
import yeomeong.common.dto.menu.response.MenuByDayResponseDto;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.kindergarten.Menu;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MenuRepository;

import java.util.List;

import static yeomeong.common.dto.menu.request.MenuCreateDto.toEntityMenu;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final KindergartenRepository kindergartenRepository;
    private final KidRepository kidRepository;


    //일자별 메뉴 가져오기
    public MenuByDayResponseDto getMenuByDay(
            MenuByDayRequestDto menuByDayRequestDto){
        //메뉴 가져오기
        MenuByDayResponseDto menuByDay = menuRepository.getMenuByDay(menuByDayRequestDto.getDay(), menuByDayRequestDto.getKindergartenId());

        //해당 유치원의 모든 아이
        List<Kid> allKidsByKindergarten = kidRepository.findAllById(menuByDayRequestDto.getKindergartenId());

        for(Kid kid : allKidsByKindergarten){

            for(String allergy : menuByDay.getLunchAllergies()){


            }
        }

        return null;
    }

    // 일자별 메뉴 생성하기
    @Transactional
    public void createMenuByDay(Long kindergartenId, MenuCreateDto menuCreateDto) {

        Kindergarten kindergarten = kindergartenRepository.findById(kindergartenId)
                .orElseThrow(()-> new RuntimeException(("Kindergarten not found")));


        Menu menu = toEntityMenu(kindergarten,menuCreateDto);

        menuRepository.save(menu);
    }

    // 일자별 메뉴 수정하기

}
