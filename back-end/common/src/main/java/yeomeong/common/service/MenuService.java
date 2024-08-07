package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.menu.request.MenuByDayRequestDto;
import yeomeong.common.dto.menu.request.MenuCreateDto;
import yeomeong.common.dto.menu.response.KidAllergyResponseDto;
import yeomeong.common.dto.menu.response.MenuByDayResponseDto;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.kindergarten.Menu;
import yeomeong.common.entity.member.Kid;
import yeomeong.common.repository.KidRepository;
import yeomeong.common.repository.KindergartenRepository;
import yeomeong.common.repository.MenuRepository;

import java.util.Arrays;
import java.util.HashSet;
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
            MenuByDayRequestDto menuByDayRequestDto) {
        //메뉴 가져오기
        MenuByDayResponseDto menuByDay = menuRepository.getMenuByDay(menuByDayRequestDto.getDay(), menuByDayRequestDto.getKindergartenId());

        //해당 유치원의 모든 아이
        List<Kid> allKidsByKindergarten = kidRepository.findAllById(menuByDayRequestDto.getKindergartenId());

        for (Kid kid : allKidsByKindergarten) { //모든 아이들에 대해서 검사

            addKidAllergiesToMenu(menuByDay.getLunchAllergies(), kid, menuByDay.getKidAllergyListOfLunch());
            addKidAllergiesToMenu(menuByDay.getSnackAllergies(), kid, menuByDay.getKidAllergyListOfSnack());
            addKidAllergiesToMenu(menuByDay.getDinnerAllergies(), kid, menuByDay.getKidAllergyListOfDinner());

        }
        return menuByDay;
    }

    private void addKidAllergiesToMenu(List<String> menuAllergies, Kid kid, List<KidAllergyResponseDto> kidAllergyList){
        for(String allergy : menuAllergies){
            if(kid.getAllergies().contains(allergy)){
                kidAllergyList.add(new KidAllergyResponseDto(
                        kid.getName(),
                        kid.getBan().getName(),
                        kid.getPicture())
                );
            }
        }
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
