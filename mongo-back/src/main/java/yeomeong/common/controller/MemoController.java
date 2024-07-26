package yeomeong.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.document.Memo;
import yeomeong.common.service.MemoService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemoController {
    private final MemoService memoService;

    @PostMapping("memo")
    public void createMemo(@RequestBody Memo memo) {
        memoService.createMemo(memo);
    }

    @GetMapping("memo/{teacher_id}/{date}")
    public List<Memo> getMemo(@PathVariable Long teacherId, @PathVariable LocalDate date) {
        return memoService.getMemosByTeacherIdAndDate(teacherId, date);
    }

    @GetMapping("memo/{teacher_id}/{date}/{kid_id}")
    public List<Memo> getMemoPerKid(@PathVariable Long teacherId, @PathVariable LocalDate date, @PathVariable Long kidId) {
        return memoService.getMemosByTeacherIdAndDateAndKidId(teacherId, date, kidId);
    }

    @PutMapping("memo/{memo_id}")
    public void updateMemo(@PathVariable String memoId, @RequestBody Memo memo) {
        memoService.updateMemo(memoId, memo);
    }

    @DeleteMapping("memo/{memo_id}")
    public void deleteMemo(@PathVariable String memoId) {
        memoService.deleteMemo(memoId);
    }
}
