package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.menu.request.MenuByDayRequestDto;
import yeomeong.common.dto.menu.request.MenuCreateDto;
import yeomeong.common.dto.menu.response.MenuByDayResponseDto;
import yeomeong.common.service.MenuService;

import java.time.LocalDate;

@RestController
@RequestMapping("/menus'")
@RequiredArgsConstructor
@Tag(name = "식단 API", description = "일자별 조회, 생성, 수정")
public class MenuController {


    private final MenuService menuService;

    //일자에 해당 유치원 식단 가져오기
    @GetMapping("/{kindergarten_id}/{year}/{month}/{day}")
    public ResponseEntity<MenuByDayResponseDto> getMenu(
            @PathVariable("kindergarten_id")Long kindergartenId,
            @PathVariable("year") int year,
            @PathVariable("month") int month,
            @PathVariable("day") int day){

        MenuByDayRequestDto requestDto = new MenuByDayRequestDto(kindergartenId, LocalDate.of(year,month,day));
        MenuByDayResponseDto menu = menuService.getMenuByDay(requestDto);

        return ResponseEntity.ok(menu);
    }

    // 해당 일자에 유치원 식단 생성하기
    @PostMapping("/{kindergarten_id}")
    public ResponseEntity<Void> createMenu(
            @PathVariable("kindergarten_id") Long kindergartenId,
            @RequestBody MenuCreateDto menuCreateDto){

        menuService.createMenuByDay(kindergartenId, menuCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //해당 일자 식단 수정하기

}
