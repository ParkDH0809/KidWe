package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.kid.KidDetailInfoDto;
import yeomeong.common.dto.kid.KidJoinRequestDto;
import yeomeong.common.dto.kid.KidUpdateInfoDto;
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
        return ResponseEntity.ok().body(kidService.getKidInfo(kidId));
    }

    @Operation(summary = "아이 정보 수정(변경) API", description = "마이페이지에서 특정 아이 정보를 수정할 때의 API입니다.")
    @PutMapping
    public ResponseEntity<Void> updateKid(@RequestBody KidUpdateInfoDto kidUpdateInfoDto) {
        kidService.updateKidInfo(kidUpdateInfoDto);
        return ResponseEntity.ok().build();
    }

}
