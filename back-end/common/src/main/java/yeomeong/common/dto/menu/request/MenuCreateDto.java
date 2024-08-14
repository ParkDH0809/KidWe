package yeomeong.common.dto.menu.request;


import jakarta.persistence.Column;
import lombok.Data;
import yeomeong.common.entity.kindergarten.Kindergarten;
import yeomeong.common.entity.kindergarten.Menu;

import java.time.LocalDate;
import java.util.List;

@Data
public class MenuCreateDto {

    private String lunch;
    private List<String> lunchAllergies;
    private String snack;
    private List<String> snackAllergies;
    private String dinner;
    private List<String> dinnerAllergies;

    @Column(unique = true)
    private LocalDate menuDate;



    public static Menu toEntityMenu(Kindergarten kindergarten, MenuCreateDto menuCreateDto){
        return Menu.builder()
                .kindergarten(kindergarten)
                .menuDate(menuCreateDto.getMenuDate())
                .lunch(menuCreateDto.getLunch())
                .lunchAllergies(listToString(menuCreateDto.lunchAllergies))
                .dinner(menuCreateDto.getDinner())
                .dinnerAllergies(listToString(menuCreateDto.dinnerAllergies))
                .snack(menuCreateDto.getSnack())
                .snackAllergies(listToString(menuCreateDto.snackAllergies)).build();
    }
    public static Menu toEntityMenu(Long id,Kindergarten kindergarten, MenuCreateDto menuCreateDto){
        return Menu.builder()
                .id(id)
                .kindergarten(kindergarten)
                .menuDate(menuCreateDto.getMenuDate())
                .lunch(menuCreateDto.getLunch())
                .lunchAllergies(listToString(menuCreateDto.lunchAllergies))
                .dinner(menuCreateDto.getDinner())
                .dinnerAllergies(listToString(menuCreateDto.dinnerAllergies))
                .snack(menuCreateDto.getSnack())
                .snackAllergies(listToString(menuCreateDto.snackAllergies)).build();
    }


    private static String listToString(List<String> list) {
        return String.join(",", list);
    }
    public static void updateEntityMenu(Menu menu, MenuCreateDto menuCreateDto) {
        menu.setMenuDate(menuCreateDto.getMenuDate());
        menu.setLunch(menuCreateDto.getLunch());
        menu.setLunchAllergies(listToString(menuCreateDto.getLunchAllergies()));
        menu.setDinner(menuCreateDto.getDinner());
        menu.setDinnerAllergies(listToString(menuCreateDto.getDinnerAllergies()));
        menu.setSnack(menuCreateDto.getSnack());
        menu.setSnackAllergies(listToString(menuCreateDto.getSnackAllergies()));
    }

}
