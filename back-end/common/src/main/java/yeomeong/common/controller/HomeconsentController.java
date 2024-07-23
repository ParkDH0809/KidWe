package yeomeong.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.entity.jpa.Homeconsent;
import yeomeong.common.service.HomeconsentService;

@RestController("homeconsents/")
@RequiredArgsConstructor
public class HomeconsentController {

    @Autowired
    private HomeconsentService homeconsentService;

    @GetMapping("{ban_id}/{year_month}")
    public ResponseEntity<Homeconsent> getHomeconsent(@PathVariable Long ban_id ){

        Homeconsent homeconsent = homeconsentService.findById(ban_id);

        return ResponseEntity.ok(homeconsent);
    }
}
