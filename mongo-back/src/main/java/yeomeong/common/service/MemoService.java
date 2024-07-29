package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.document.Memo;
import yeomeong.common.dto.MemoRequestDto;
import yeomeong.common.dto.MemoResponseDto;
import yeomeong.common.repository.MemoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    // 메모 생성하기
    public Memo createMemo(MemoRequestDto memoRequestDto) {
        return memoRepository.save(memoRequestDto.toDocument());
    }

    public Memo getMemo(String id){
        return memoRepository.findMemoById(id);
    }

    // 날짜별 메모 조회하기
    public List<MemoResponseDto> getMemosByTeacherIdAndDate(Long teacherId, String date) {
        List<Memo> memos = memoRepository.findByTeacherIdAndDate(teacherId, date);
        if(memos == null){
            return null;
        }

        List<MemoResponseDto> memoResponseDtos = new ArrayList<>();
        for(Memo memo : memos){
            memoResponseDtos.add(new MemoResponseDto(memo));
        }
        return memoResponseDtos;
    }

    // 날짜별 아이별 메모 조회하기
    public List<MemoResponseDto> getMemosByTeacherIdAndDateAndKidId(Long teacherId, String date, Long kidId) {
        List<Memo> memos = memoRepository.findByTeacherIdAndDateAndKidId(teacherId, date, kidId);
        if(memos == null){
            return null;
        }

        List<MemoResponseDto> memoResponseDtos = new ArrayList<>();
        for(Memo memo : memos){
            memoResponseDtos.add(new MemoResponseDto(memo));
        }
        return memoResponseDtos;
    }

    // 메모 수정하기
    public MemoResponseDto updateMemo(String id, MemoRequestDto updatedMemo) {
        Optional<Memo> memo = memoRepository.findById(id);
        if(memo.isPresent()){
            Memo existMemo = memo.get();
            if(existMemo.getIsDeleted() == true) return null;
            existMemo = updatedMemo.toDocument();
            return new MemoResponseDto(memoRepository.save(existMemo));
        }

        return null;
    }

    // 메모 삭제하기
    public void deleteMemo(String id) {
        Memo memo = memoRepository.findMemoById(id);
        memo.setIsDeleted(true);
        memoRepository.save(memo);
    }
}
