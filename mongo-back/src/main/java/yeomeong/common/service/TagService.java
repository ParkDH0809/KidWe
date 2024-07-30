package yeomeong.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeomeong.common.document.Tag;
import yeomeong.common.dto.TagResponseDto;
import yeomeong.common.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    //선생님별로 Tag 조회하기
    public List<Tag> getTagsByTeacherId(Long teacherId){
        List<Tag> tags = tagRepository.findTagByTeacherId(teacherId);
        if(tags == null) return null;
        return tags;
    }

    //Tag 생성하기
    public Tag createTag(Tag tag){
        Tag isExistTag = tagRepository.findTagByTeacherIdAndContet(tag.getTeacherId(), tag.getContent());
        if(isExistTag == null){
            return tagRepository.save(tag);
        }
        return null;
    }

    //Tag 수정하기
    public Tag updateTag(Tag updatedTag){
        Tag oldTag = tagRepository.findTagByTeacherIdAndContet(updatedTag.getTeacherId(), updatedTag.getContent());
        if(oldTag == null){
            return createTag(updatedTag);
        }
        updatedTag.setCount(updatedTag.getCount() + 1);
        return tagRepository.save(updatedTag);
    }
}
