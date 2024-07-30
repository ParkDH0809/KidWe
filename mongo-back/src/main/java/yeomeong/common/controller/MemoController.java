package yeomeong.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.MemoRequestDto;
import yeomeong.common.dto.MemoResponseDto;
import yeomeong.common.service.MemoService;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/memo")
public class MemoController {
    private final MemoService memoService;

    @PostMapping("/{teacher_id}")
    public ResponseEntity<MemoResponseDto> createMemo(@PathVariable("teacher_id") Long teacherId,
                                                      @RequestBody MemoRequestDto memoDto) {
        MemoResponseDto createdMemoResponseDto = memoService.createMemo(teacherId, memoDto);
        if (createdMemoResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(createdMemoResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{teacher_id}/{year}/{month}/{day}")
    public ResponseEntity<List<MemoResponseDto>> getMemos(@PathVariable("teacher_id") Long teacherId,
                                                          @PathVariable("year") String year,
                                                          @PathVariable("month") String month,
                                                          @PathVariable("day") String day) {
        String date = year + "-" + month + "-" + day;
        List<MemoResponseDto> memoResponseDtos = memoService.getMemosByTeacherIdAndDate(teacherId, date);

        if (memoResponseDtos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(memoResponseDtos, HttpStatus.OK);
        }
    }

    @GetMapping("/{teacher_id}/{year}/{month}/{day}/{kid_id}")
    public ResponseEntity<List<MemoResponseDto>> getMemosPerKid(@PathVariable("teacher_id") Long teacherId,
                                                                @PathVariable("year") String year,
                                                                @PathVariable("month") String month,
                                                                @PathVariable("day") String day,
                                                                @PathVariable("kid_id") Long kidId) {
        String date = year + "-" + month + "-" + day;
        List<MemoResponseDto> memoResponseDtos = memoService.getMemosByTeacherIdAndDateAndKidId(teacherId, date, kidId);
        if (memoResponseDtos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(memoResponseDtos, HttpStatus.OK);
        }
    }

    @PutMapping("/{teacher_id}/{memo_id}")
    public ResponseEntity<MemoResponseDto> updateMemo(@PathVariable("teacher_id") Long teacherId,
                                                      @PathVariable("memo_id") String memoId,
                                                      @RequestBody MemoRequestDto memoRequestDto) {
        MemoResponseDto updataedMemoResponseDto = memoService.updateMemo(teacherId, memoId, memoRequestDto);
        if(updataedMemoResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updataedMemoResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{teacher_id}/{memo_id}")
    public ResponseEntity<String> deleteMemo(@PathVariable("teacher_id") Long teacherId,
                                             @PathVariable("memo_id") String memoId) {
        if(!memoService.deleteMemo(teacherId, memoId)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            return new ResponseEntity<>("Success Deleted", HttpStatus.OK);
        }
    }
}