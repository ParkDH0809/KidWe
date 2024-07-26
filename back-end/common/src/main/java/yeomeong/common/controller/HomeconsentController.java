package yeomeong.common.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.entity.jpa.Homeconsent;
import yeomeong.common.service.HomeconsentService;

@RestController("/homeconsents")
@RequiredArgsConstructor
@Tag(name = "귀가동의서 API")
public class HomeconsentController {

    @Autowired
    private HomeconsentService homeconsentService;

    @GetMapping("/{ban_id}/{year}/{month}")
    public ResponseEntity<Homeconsent> getHomeconsent(
            @PathVariable Long ban_id,
            @PathVariable int year,
            @PathVariable int month){

        Homeconsent homeconsent = homeconsentService.findById(ban_id);

        return ResponseEntity.ok(homeconsent);
    }
}
