package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.announcement.VoteCreateDto;
import yeomeong.common.dto.post.announcement.VoteResultDto;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.Vote;
import yeomeong.common.repository.VoteRepository;
import yeomeong.common.repository.jpa.AnnouncementRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final VoteRepository voteRepository;
    private final AnnouncementRepository announcementRepository;

    @Transactional
    public void createVote(Long announcementId, VoteCreateDto voteCreateDto) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("해당 공지사항을 찾을 수 없습니다"));

        Vote vote = new Vote(voteCreateDto.getTitle(),
                voteCreateDto.getVoteStartDate(),
                voteCreateDto.getVoteEndDate(),
                voteCreateDto.getCandidate());

        announcement.setVote(vote);

        voteRepository.save(vote);
    }


    public VoteResultDto doVote(Long voteId, String voteItem) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("해당하는 투표 id가 없습니다"));

        vote.doVote(voteItem);

        return new  VoteResultDto(vote.getItems());

    }
}
