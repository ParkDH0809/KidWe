package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.kindergarten.KindergartenSaveRequestDto;
import yeomeong.common.service.KindergartenService;

@RestController
@Tag(name = "유치원 API", description = "유치원 관련 API")
public class KindergartenController {

    final KindergartenService kindergartenService;

    public KindergartenController(KindergartenService kindergartenService) {
        this.kindergartenService = kindergartenService;
    }

    @Operation(summary = "유치원 생성", description = "유치원을 생성합니다.")
    @PostMapping("/kindergarten")
    public ResponseEntity<Void> createKindergarten(@RequestBody KindergartenSaveRequestDto kindergartenSaveRequestDto) {
        kindergartenService.createKindergarten(kindergartenSaveRequestDto);
        return ResponseEntity.ok().build();
    }

}
