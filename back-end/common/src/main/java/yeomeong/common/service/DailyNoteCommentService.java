package yeomeong.common.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.dailynote.request.DailyNoteCommentCreateRequestDto;
import yeomeong.common.dto.post.dailynote.request.DailyNoteCommentUpdateRequestDto;
import yeomeong.common.dto.post.dailynote.response.DailyNoteCommentResponseDto;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.post.DailyNote;
import yeomeong.common.entity.post.comment.DailyNoteComment;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.exception.ErrorResponse;
import yeomeong.common.repository.DailyNoteCommentRepository;
import yeomeong.common.repository.DailyNoteRepository;
import yeomeong.common.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class DailyNoteCommentService {
    private final MemberRepository memberRepository;
    private final DailyNoteRepository dailyNoteRepository;
    private final DailyNoteCommentRepository dailyNoteCommentRepository;

    // 알림장에 (대)댓글 생성하기
    @Transactional
    public DailyNoteCommentResponseDto createDailyNoteComment(
        DailyNoteCommentCreateRequestDto dailyNoteCommentCreateRequestDto){
        DailyNote dailyNote = dailyNoteRepository.findById(dailyNoteCommentCreateRequestDto.getDailynoteId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_ID)
        );

        Member member =  memberRepository.findById(dailyNoteCommentCreateRequestDto.getMemberId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_ID)
        );
        //댓글이면
        if(dailyNoteCommentCreateRequestDto.getParentCommentId() == null){
            return new DailyNoteCommentResponseDto(dailyNoteCommentRepository.save(dailyNoteCommentCreateRequestDto.toEntity(dailyNote, member, null)));
        }
        //대댓글이면
        else{
            final Long parentCommentId = dailyNoteCommentCreateRequestDto.getParentCommentId();
            DailyNoteComment parentComment = dailyNoteCommentRepository.findById(parentCommentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_WRITER)
            );
            return new DailyNoteCommentResponseDto(dailyNoteCommentRepository.save(dailyNoteCommentCreateRequestDto.toEntity(dailyNote, member, parentComment)));
        }
    }

    // 알림장에 (대)댓글 수정하기
    @Transactional
    public DailyNoteCommentResponseDto updateDailyNoteComment(
        Long dailyNoteCommentId,
        DailyNoteCommentUpdateRequestDto dailyNoteCommentUpdateRequestDto){
        DailyNoteComment oldDailyNoteComment = dailyNoteCommentRepository.findById(dailyNoteCommentId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_COMMENT_ID)
        );
        oldDailyNoteComment.setNewContent(dailyNoteCommentUpdateRequestDto.getContent());
        oldDailyNoteComment.update();
        return new DailyNoteCommentResponseDto(dailyNoteCommentRepository.save(oldDailyNoteComment));
    }
    
    // 알림장에 (대)댓글 삭제하기
    public void deleteDailyNoteComment(Long dailyNoteCommentId){
        DailyNoteComment oldDailyNoteComment = dailyNoteCommentRepository.findById(dailyNoteCommentId).orElseThrow(
             () -> new CustomException(ErrorCode.NOT_FOUND_DAILYNOTE_COMMENT_ID)
        );
        oldDailyNoteComment.delete();
    }
}
