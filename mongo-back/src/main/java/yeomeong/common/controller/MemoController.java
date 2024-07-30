package yeomeong.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.document.Memo;
import yeomeong.common.dto.MemoRequestDto;
import yeomeong.common.dto.MemoResponseDto;
import yeomeong.common.service.MemoService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/memo")

public class MemoController {
    private final MemoService memoService;

    @PostMapping("/")
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto memoDto) {
        Memo createdMemo = memoService.createMemo(memoDto);
        if (createdMemo == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(createdMemo.getIsDeleted() == true){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MemoResponseDto memoResponseDto = new MemoResponseDto(memoService.getMemo(createdMemo.getId()));
        return new ResponseEntity<>(memoResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{teacher_id}/{date}")
    public ResponseEntity<List<MemoResponseDto>> getMemos(@PathVariable("teacher_id") Long teacherId,
                                              @PathVariable("date") String date) {
        List<MemoResponseDto> memos = memoService.getMemosByTeacherIdAndDate(teacherId, date);

        if (memos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(memos, HttpStatus.OK);
        }
    }

    @GetMapping("/{teacher_id}/{date}/{kid_id}")
    public ResponseEntity<List<MemoResponseDto>> getMemosPerKid(@PathVariable("teacher_id") Long teacherId,
                                    @PathVariable("date") String date,
                                    @PathVariable("kid_id") Long kidId) {
        List<MemoResponseDto> memos = memoService.getMemosByTeacherIdAndDateAndKidId(teacherId, date, kidId);
        if (memos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(memos, HttpStatus.OK);
        }
    }

    @PutMapping("/{memo_id}")
    public ResponseEntity updateMemo(@PathVariable("memo_id") String memoId, @RequestBody MemoRequestDto memoRequestDto) {
        memoService.updateMemo(memoId, memoRequestDto);
        // 에러처리 해야합니다
        return new ResponseEntity<>(memoService.getMemo(memoId), HttpStatus.OK);
    }

    @DeleteMapping("/{memo_id}")
    public ResponseEntity deleteMemo(@PathVariable("memo_id") String memoId) {
        memoService.deleteMemo(memoId);
        return new ResponseEntity<>("Success Deleted", HttpStatus.OK);
    }
}