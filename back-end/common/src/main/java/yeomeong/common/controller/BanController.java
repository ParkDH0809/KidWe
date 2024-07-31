package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.ban.BanCreateRequestDto;
import yeomeong.common.service.BanService;

@RestController
@RequestMapping("/ban")
@Tag(name = "반 API", description = "반 관련 API")
public class BanController {

    final BanService banService;

    public BanController(BanService banService) {
        this.banService = banService;
    }

    @Operation(summary = "반 생성", description = "반을 생성합니다.")
    @PostMapping
    public ResponseEntity<Void> createBan(
        @RequestBody BanCreateRequestDto banCreateRequestDto) {
        banService.createBan(banCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
