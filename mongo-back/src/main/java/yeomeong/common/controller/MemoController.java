package yeomeong.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.document.Memo;
import yeomeong.common.dto.MemoRequestDto;
import yeomeong.common.service.MemoService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/memo")

public class MemoController {
    private final MemoService memoService;

    @PostMapping("/")
    public void createMemo(@RequestBody MemoRequestDto memoDto) {
        memoService.createMemo(memoDto);
    }

    @GetMapping("/{teacher_id}/{date}")
    public ResponseEntity<List<Memo>> getMemo(@PathVariable("teacher_id") Long teacherId,
                                              @PathVariable("date") String date) {
        List<Memo> memos = memoService.getMemosByTeacherIdAndDate(teacherId, date);

        if (memos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(memos, HttpStatus.OK);
        }
    }

    @GetMapping("/{teacher_id}/{date}/{kid_id}")
    public List<Memo> getMemoPerKid(@PathVariable("teacher_id") Long teacherId, @PathVariable("date") String date, @PathVariable("kid_id") Long kidId) {
        return memoService.getMemosByTeacherIdAndDateAndKidId(teacherId, date, kidId);
    }

    @PutMapping("/{memo_id}")
    public void updateMemo(@PathVariable("memo_id") String memoId, @RequestBody Memo memo) {
        memoService.updateMemo(memoId, memo);
    }

    @DeleteMapping("/{memo_id}")
    public void deleteMemo(@PathVariable("memo_id") String memoId) {
        memoService.deleteMemo(memoId);
    }
}