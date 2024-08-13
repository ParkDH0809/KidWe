package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.document.Tag;
import yeomeong.common.dto.TagRequestDto;
import yeomeong.common.dto.TagResponseDto;
import yeomeong.common.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    //선생님별로 Tag 조회하기
    @Transactional
    public List<TagResponseDto> getTagsByTeacherId(Long teacherId) {
        List<Tag> tags = tagRepository.findTagByTeacherId(teacherId);
        if (tags == null) {
            return null;
        }

        List<TagResponseDto> tagResponseDtos = new ArrayList<>();
        for (Tag tag : tags) {
            tagResponseDtos.add(new TagResponseDto(tag));
        }
        return tagResponseDtos;
    }

    //Tag 생성하기
    @Transactional
    public TagResponseDto createTag(TagRequestDto tagRequestDto) {
        Tag isExistTag = tagRepository.findTagByTeacherIdAndContet(tagRequestDto.getTeacherId(),
            tagRequestDto.getContent());
        if (isExistTag == null) {
            return new TagResponseDto(tagRepository.save(tagRequestDto.toDocument()));
        }
        return null;
    }

    //Tag 수정하기
    @Transactional
    public List<TagResponseDto> updateTag(Long teacheriId, List<TagRequestDto> updatedTagIds) {
        List<Tag> oldTags = tagRepository.findTagByTeacherId(teacheriId);
        if (oldTags == null) {
            return null;
        }

        List<TagResponseDto> updatedTags = new ArrayList<>();
        for (Tag oldTag : oldTags) {
            for (TagRequestDto updateTagRequestDto : updatedTagIds) {
                if (updateTagRequestDto.getId().equals(oldTag.getId())) {
                    oldTag.count();
                    updatedTags.add(new TagResponseDto(tagRepository.save(oldTag)));
                } else {
                    updatedTags.add(new TagResponseDto(oldTag));
                }
            }

        }
        return updatedTags;
    }
}
