package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.ban.BanDetailInfoResponseDto;
import yeomeong.common.service.BanService;

@RestController
@RequestMapping("/bans")
@Tag(name = "반 API", description = "반 관련 API")
public class BanController {

    final BanService banService;

    public BanController(BanService banService) {
        this.banService = banService;
    }

    @Operation(summary = "특정 반 정보 조회(반 이름, 반 구성 아이)", description = "특정 반 정보(반 ID, 반 이름, 아이, 선생님)를 조회합니다.")
    @GetMapping("{banId}")
    public ResponseEntity<BanDetailInfoResponseDto> getBanInfo(@PathVariable Long banId) {
        return ResponseEntity.status(HttpStatus.OK).body(banService.getBanInfo(banId));
    }

}
