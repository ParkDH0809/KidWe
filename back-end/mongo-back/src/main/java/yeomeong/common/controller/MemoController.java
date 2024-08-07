package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import yeomeong.common.dto.MemoRequestDto;
import yeomeong.common.dto.MemoResponseDto;
import yeomeong.common.service.MemoService;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/memo")
@Tag(name = "메모", description = "메모 관련 API")
public class MemoController {

    private final MemoService memoService;

    @Operation(summary = "메모 생성", description = "새로운 메모를 생성하고, 태그를 업데이트 합니다.")
    @PostMapping("/{teacher_id}")
    public ResponseEntity<MemoResponseDto> createMemo(@PathVariable("teacher_id") Long teacherId,
        @RequestBody MemoRequestDto memoDto) {
        MemoResponseDto createdMemoResponseDto = memoService.createMemo(teacherId, memoDto);
        if (createdMemoResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(createdMemoResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "날짜별 메모 조회", description = "선생님ID, 년, 월, 일을 입력받아 해당 조건에 만족하는 메모들을 조회해 List로 반환합니다")
    @GetMapping("/{teacher_id}/{year}/{month}/{day}")
    public ResponseEntity<List<MemoResponseDto>> getMemos(
        @PathVariable("teacher_id") Long teacherId,
        @PathVariable("year") String year,
        @PathVariable("month") String month,
        @PathVariable("day") String day) {
        String date = year + "-" + month + "-" + day;
        List<MemoResponseDto> memoResponseDtos = memoService.getMemosByTeacherIdAndDate(teacherId,
            date);

        if (memoResponseDtos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(memoResponseDtos, HttpStatus.OK);
        }
    }

    @Operation(summary = "아이별 메모 조회", description = "선생님ID, 년, 월, 일, 아이ID를  입력받아 해당 조건에 만족하는 메모들을 조회해 List로 반환합니다")
    @GetMapping("/{teacher_id}/{year}/{month}/{day}/{kid_id}")
    public ResponseEntity<List<MemoResponseDto>> getMemosPerKid(
        @PathVariable("teacher_id") Long teacherId,
        @PathVariable("year") String year,
        @PathVariable("month") String month,
        @PathVariable("day") String day,
        @PathVariable("kid_id") Long kidId) {
        String date = year + "-" + month + "-" + day;
        List<MemoResponseDto> memoResponseDtos = memoService.getMemosByTeacherIdAndDateAndKidId(
            teacherId, date, kidId);
        if (memoResponseDtos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(memoResponseDtos, HttpStatus.OK);
        }
    }

    @Operation(summary = "특정 메모 조회", description = "선생님ID, 년, 월, 일, 아이ID를  입력받아 해당 조건에 만족하는 메모들을 조회해 List로 반환합니다")
    @GetMapping("/{teacher_id}/{memo_id}")
    public ResponseEntity<MemoResponseDto> getMemo(
        @PathVariable("teacher_id") Long teacherId,
        @PathVariable("memo_id") String memoId ) {
        MemoResponseDto memoResponseDto = memoService.getMemo(memoId);
        if (memoResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(memoResponseDto, HttpStatus.OK);
        }
    }

    @Operation(summary = "메모 수정하기", description = "선생님ID, 메모ID, 그리고 수정된 메모를 입력받아 메모를 수정합니다")
    @PutMapping("/{teacher_id}/{memo_id}")
    public ResponseEntity<MemoResponseDto> updateMemo(@PathVariable("teacher_id") Long teacherId,
        @PathVariable("memo_id") String memoId,
        @RequestBody MemoRequestDto memoRequestDto) {
        MemoResponseDto updataedMemoResponseDto = memoService.updateMemo(teacherId, memoId,
            memoRequestDto);
        if (updataedMemoResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updataedMemoResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "메모 삭제하기", description = "선생님ID, 메모ID를 입력받아 메모를 삭제(메모ID)합니다")
    @DeleteMapping("/{teacher_id}/{memo_id}")
    public ResponseEntity<String> deleteMemo(@PathVariable("teacher_id") Long teacherId,
        @PathVariable("memo_id") String memoId) {
        if (!memoService.deleteMemo(teacherId, memoId)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>("Success Deleted", HttpStatus.OK);
        }
    }
}