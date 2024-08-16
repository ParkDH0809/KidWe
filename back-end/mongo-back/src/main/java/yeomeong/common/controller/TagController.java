package yeomeong.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yeomeong.common.dto.TagRequestDto;
import yeomeong.common.dto.TagResponseDto;
import yeomeong.common.service.TagService;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/tags")
@Tag(name = "태그", description = "태그 관련 API")
public class TagController {

    private final TagService tagService;

//    @PostMapping("/")
//    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagRequestDto tagRequestDto) {
//        TagResponseDto createdTag = tagService.createTag(tagRequestDto);
//        if(createdTag == null) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        else{
//            return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
//        }
//    }

    @Operation(summary = "태그 조회", description = "선생님ID로 Tag들을 조회해옵니다.")
    @GetMapping("/{teacher_id}")
    public ResponseEntity<List<TagResponseDto>> getTags(
        @PathVariable("teacher_id") Long teacherId) {
        List<TagResponseDto> tags = tagService.getTagsByTeacherId(teacherId);
        if (tags == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tags, HttpStatus.OK);
        }
    }

//    @PutMapping("/{teacher_id}")
//    public ResponseEntity<List<TagResponseDto>> updateTags(@PathVariable("teacher_id") Long teacherId, @RequestBody List<TagRequestDto> tagRequestDtos){
//        List<TagResponseDto> updatedTags = tagService.updateTag(teacherId, tagRequestDtos);
//        if(updatedTags == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(updatedTags, HttpStatus.OK);
//    }
}
