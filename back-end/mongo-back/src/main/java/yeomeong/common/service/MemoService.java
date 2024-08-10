package yeomeong.common.service;

import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.document.Memo;
import yeomeong.common.document.Tag;
import yeomeong.common.dto.MemoRequestDto;
import yeomeong.common.dto.MemoResponseDto;
import yeomeong.common.dto.TagRequestDto;
import yeomeong.common.dto.TagResponseDto;
import yeomeong.common.repository.MemoRepository;
import yeomeong.common.repository.TagRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final TagRepository tagRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public List<Tag> updateTag(List<TagRequestDto> tagRequestDtos) {
        // 메모의 tag 생성&수정(빈도수 1 증가)하기
        if (tagRequestDtos == null) {
            return null;
        }

        List<Tag> tags = new ArrayList<Tag>();
        if (tagRequestDtos != null && !tagRequestDtos.isEmpty()) {
            for (TagRequestDto tagRequestDto : tagRequestDtos) {
                // 이미 존재한하는 Tag라면 수정(빈도수 1 증가)하기
                if (tagRequestDto.getId() != null) {
                    Tag oldTag = tagRepository.findById(tagRequestDto.getId()).orElse(null);
                    if (oldTag == null) {
                        return null;
                    }
                    oldTag.setCount(oldTag.getCount() + 1);
                    tags.add(tagRepository.save(oldTag));
                }
                // 처음 사용하는 Tag라면 생성하기
                else {
                    tags.add(tagRepository.save(tagRepository.save(tagRequestDto.toDocument())));
                }
            }
        }
        return tags;
    }

    // 메모 생성하기
    @Transactional
    public MemoResponseDto createMemo(Long teacherId, MemoRequestDto memoRequestDto) {
        List<Tag> tags = updateTag(memoRequestDto.getTags());
        Memo updatedTagAndNotUpdatedMemo = memoRequestDto.toDocument(teacherId);
        updatedTagAndNotUpdatedMemo.setNewTags(tags);
        return new MemoResponseDto(memoRepository.save(updatedTagAndNotUpdatedMemo));
    }

    public MemoResponseDto getMemo(String id) {
        return new MemoResponseDto(memoRepository.findMemoById(id));
    }

    // 날짜별 메모 조회하기
    @Transactional
    public List<MemoResponseDto> getMemosByTeacherIdAndDate(Long teacherId, String date) {
        List<Memo> memos = memoRepository.findByTeacherIdAndDate(teacherId, date);
        if (memos == null) {
            return null;
        }

        List<MemoResponseDto> memoResponseDtos = new ArrayList<>();
        for (Memo memo : memos) {
            memoResponseDtos.add(new MemoResponseDto(memo));
        }
        return memoResponseDtos;
    }

    // 날짜별 아이별 메모 조회하기
    @Transactional
    public List<MemoResponseDto> getMemosByTeacherIdAndKidIdAndDate(Long teacherId,
        Long kidId,
        String date) {
        List<Memo> memos = memoRepository.findByTeacherIdAndKidIdAndDate(teacherId, kidId, date);
        if (memos == null) {
            return null;
        }

        List<MemoResponseDto> memoResponseDtos = new ArrayList<>();
        for (Memo memo : memos) {
            memoResponseDtos.add(new MemoResponseDto(memo));
        }
        return memoResponseDtos;
    }

    // 메모 수정하기
    @Transactional
    public MemoResponseDto updateMemo(Long teacherId, String id, MemoRequestDto updatedMemoDto) {
        Memo memo = memoRepository.findMemoByTeacherIdAndId(id, teacherId);
        if (memo != null) {
            List<Tag> tags = updateTag(updatedMemoDto.getTags());

            memo.setNewUpdatedTime(LocalDateTime.parse(updatedMemoDto.getUpdatedTime(), formatter));
            memo.setNewLesson(updatedMemoDto.getLesson());
            memo.setNewKids(updatedMemoDto.getKids());
            memo.setNewTags(tags);
            memo.setNewContent(updatedMemoDto.getContent());
            // memo의 tag와 updatedMemo의 tag 비교해서 저장하기
            return new MemoResponseDto(memoRepository.save(memo));
        }
        return null;
    }

    // 메모 삭제하기
    @Transactional
    public boolean deleteMemo(Long teacherId, String id) {
        Memo memo = memoRepository.findMemoByTeacherIdAndId(id, teacherId);
        memo.delete();
        return memoRepository.save(memo).getIsDeleted();
    }
}
