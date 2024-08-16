package yeomeong.common.dto.menu.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuByDayResponseDto {

    private Long menuId;

    private String lunch;
    private List<String> lunchAllergies;
    private List<KidAllergyResponseDto> kidAllergyListOfLunch = new ArrayList<>();

    private String snack;
    private List<String> snackAllergies;
    private List<KidAllergyResponseDto> kidAllergyListOfSnack = new ArrayList<>();

    private String dinner;
    private List<String> dinnerAllergies;
    private List<KidAllergyResponseDto> kidAllergyListOfDinner = new ArrayList<>();

    public MenuByDayResponseDto(Long menuId,String lunch, String lunchAllergies, String snack, String snackAllergies, String dinner, String dinnerAllergies){
        this.menuId = menuId;
        this.lunch = lunch;
        this.lunchAllergies = parseAllergies(lunchAllergies);
        this.dinner = dinner;
        this.dinnerAllergies = parseAllergies(dinnerAllergies);
        this.snack = snack;
        this.snackAllergies = parseAllergies(snackAllergies);

    }

    private List<String> parseAllergies(String allergies){
        List<String> allergiesList = new ArrayList<>();

        if(allergies != null && !allergies.isEmpty()){
            String[] allergiesArray = allergies.split(",");
            allergiesList.addAll(Arrays.asList(allergiesArray));
        }

        return allergiesList;
    }

}
