package yeomeong.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeomeong.common.document.Tag;
import yeomeong.common.dto.TagResponseDto;
import yeomeong.common.service.TagService;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @GetMapping("/{teacher_id}")
    public ResponseEntity<List<Tag>> getTag(@PathVariable("teahcer_id") Long teacher_id) {
        List<Tag> tags = tagService.getTagsByTeacherId(teacher_id);
        if(tags == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(tags, HttpStatus.OK);
        }
    }
}
