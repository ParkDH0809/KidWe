package yeomeong.common.service;

import ai.bareun.tagger.Tagged;
import ai.bareun.tagger.Tagger;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import yeomeong.common.document.Memo;
import yeomeong.common.document.Tag;
import yeomeong.common.dto.BareunRequestDto;
import yeomeong.common.dto.BareunResponseDto;
import yeomeong.common.dto.BareunResponseDto.Sentence;
import yeomeong.common.dto.MemoRequestDto;
import yeomeong.common.dto.MemoResponseDto;
import yeomeong.common.dto.Message;
import yeomeong.common.dto.OpenAiRequestDto;
import yeomeong.common.dto.OpenAiResponseDto;
import yeomeong.common.dto.TagRequestDto;
import yeomeong.common.dto.TagResponseDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
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
    @Value(("${bareun.api-key"))
    String bareunApiKey;
    @Value("${bareun.host}")
    String bareunHost;

    private final MemoRepository memoRepository;
    private final TagRepository tagRepository;

    private String getMorpheme(String content){
        Tagged tag = new Tagger(bareunHost, bareunApiKey).tag(content);
        return tag.morphs().get(0);
    }

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
                    oldTag.count();
                    tags.add(tagRepository.save(oldTag));
                }
                // 처음 사용하는 Tag라면 생성하기
                else {
                    String morpheme = getMorpheme(tagRequestDto.getContent());
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
        System.out.println(date);
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
    public List<MemoResponseDto> getMemosByTeacherIdAndDateAndKidId(Long teacherId,
        String date,
        Long kidId) {
        List<Memo> memos = memoRepository.findByTeacherIdAndDateAndKidId(teacherId, date, kidId);
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

            if(updatedMemoDto.getUpdatedTime() != null) memo.setNewUpdatedTime(updatedMemoDto.getUpdatedTime());
            memo.getDate();
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
