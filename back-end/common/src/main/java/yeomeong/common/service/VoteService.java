package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.post.announcement.VoteCreateDto;
import yeomeong.common.dto.post.announcement.VoteItemDto;
import yeomeong.common.dto.post.announcement.VoteItemRequestDto;
import yeomeong.common.dto.post.announcement.VoteResultDto;
import yeomeong.common.entity.post.Announcement;
import yeomeong.common.entity.post.Vote;
import yeomeong.common.entity.post.VoteItem;
import yeomeong.common.repository.AnnouncementRepository;
import yeomeong.common.repository.VoteItemRepository;
import yeomeong.common.repository.VoteRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
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


        for(  VoteItemRequestDto voteItemDto :  voteCreateDto.getItems()) {

            VoteItem voteItem = new VoteItem(voteItemDto.getItemName());

            vote.getItems().add(voteItem);
            voteItem.setVote(vote);
            voteItemRepository.save(voteItem);
        }

        vote.setAnnouncement(announcement);

        announcement.setVote(vote);

        voteRepository.save(vote);
    }

    @Transactional
    public VoteResultDto doVote(Long voteId, Long voteItemId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("해당하는 투표 id가 없습니다"));

        VoteItem voteItem = voteItemRepository.findById(voteItemId)
                        .orElseThrow(() -> new RuntimeException("해당 투표 아이템을 찾을 수 없습니다."));

        voteItem.setValue(voteItem.getValue()+1);

        return new VoteResultDto(voteItem.getId(), voteItem.getItemName(), voteItem.getValue());

    }
}
