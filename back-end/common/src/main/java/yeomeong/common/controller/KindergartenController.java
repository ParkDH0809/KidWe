package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.dto.ban.BanDetailInfoResponseDto;
import yeomeong.common.dto.kindergarten.KindergartenInfoResponseDto;
import yeomeong.common.dto.kindergarten.KindergartenSearchRequestDto;
import yeomeong.common.service.BanService;
import yeomeong.common.service.KindergartenService;

@RestController
@RequestMapping("/kindergartens")
@Tag(name = "유치원 API", description = "유치원 관련 API")
public class KindergartenController {

    final KindergartenService kindergartenService;
    final BanService banService;

    public KindergartenController(KindergartenService kindergartenService, BanService banService) {
        this.kindergartenService = kindergartenService;
        this.banService = banService;
    }

    @Operation(summary = "유치원 검색", description = "검색된 유치원 리스트를 반환합니다. 만약 query의 모든 값이 null이라면 모든 유치원 리스트를 반환합니다.")
    @GetMapping
    public ResponseEntity<List<KindergartenInfoResponseDto>> getSearchedKindergartens(
        @ParameterObject KindergartenSearchRequestDto kindergartenSearchRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(kindergartenService.getSearchedKindergartenInfo(kindergartenSearchRequestDto));
    }

    @Operation(summary = "특정 유치원 기본 정보 조회", description = "특정 유치원 기본 정보를 조회합니다.")
    @GetMapping("/{kindergartenId}")
    public ResponseEntity<KindergartenInfoResponseDto> getKindergarten(@PathVariable Long kindergartenId) {
        return ResponseEntity.status(HttpStatus.OK).body(kindergartenService.getKindergartenInfo(kindergartenId));
    }

    @Operation(summary = "특정 유치원 상세 정보 조회", description = "유치원 별 반 상세 정보(이름, 아이, 선생님)를 조회합니다.")
    @GetMapping("/{kindergartenId}/detail")
    public ResponseEntity<List<BanDetailInfoResponseDto>> getBansDetailByKindergarten(@PathVariable Long kindergartenId) {
        return ResponseEntity.status(HttpStatus.OK).body(banService.getBansIdByKindergartenId(kindergartenId));
    }

}
