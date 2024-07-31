package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.announcement.VoteCreateDto;
import yeomeong.common.dto.post.announcement.VoteItemDto;
import yeomeong.common.dto.post.announcement.VoteResultDto;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.Vote;
import yeomeong.common.entity.post.VoteItem;
import yeomeong.common.repository.AnnouncementRepository;
import yeomeong.common.repository.VoteItemRepository;
import yeomeong.common.repository.VoteRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final VoteRepository voteRepository;
    private final AnnouncementRepository announcementRepository;
    private final VoteItemRepository voteItemRepository;


    @Transactional
    public void createVote(Long announcementId, VoteCreateDto voteCreateDto) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("해당 공지사항을 찾을 수 없습니다"));

        Vote vote = new Vote(voteCreateDto.getTitle(),
                voteCreateDto.getVoteStartDate(),
                voteCreateDto.getVoteEndDate(),
                announcement);


        for(  VoteItemDto voteItemDto :  voteCreateDto.getItems()) {

            VoteItem voteItem = new VoteItem(voteItemDto.getItemName()
                    , voteItemDto.getValue());

            vote.getItems().add(voteItem);
            voteItem.setVote(vote);
            voteItemRepository.save(voteItem);
        }

        vote.setAnnouncement(announcement);

        announcement.setVote(vote);

        voteRepository.save(vote);
    }


    public VoteResultDto doVote(Long voteId, int index) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("해당하는 투표 id가 없습니다"));

        int value =  vote.getItems().get(index).getValue();

        vote.getItems().get(index).setValue(value + 1);

        return new VoteResultDto(vote.getItems());

    }
}
