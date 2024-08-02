package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.kid.KidDetailInfoDto;
import yeomeong.common.dto.kid.KidJoinRequestDto;
import yeomeong.common.service.KidService;

@RestController
@RequestMapping("/kids")
@Tag(name = "아이 API", description = "아이 관련 API")
public class KidController {

    final KidService kidService;

    public KidController(KidService kidService) {
        this.kidService = kidService;
    }

    @Operation(summary = "아이 정보 생성 API", description = "아이 정보를 입력(생성)합니다.")
    @PostMapping
    public ResponseEntity<Void> joinKid(@RequestBody KidJoinRequestDto kidJoinRequestDto) {
        kidService.joinKid(kidJoinRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "아이 정보 조회 API", description = "특정 아이 정보를 조회합니다.")
    @GetMapping("/{kidId}")
    public ResponseEntity<KidDetailInfoDto> getKid(@PathVariable Long kidId) {
        return ResponseEntity.status(HttpStatus.OK).body(kidService.getKid(kidId));
    }

}
