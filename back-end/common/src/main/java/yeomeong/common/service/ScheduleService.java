package yeomeong.common.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import yeomeong.common.dto.schedule.CreateScheduleRequestDto;
import yeomeong.common.dto.schedule.CreateScheduleResponseDto;
import yeomeong.common.dto.schedule.ScheduleByDayListDto;
import yeomeong.common.entity.Schedule;
import yeomeong.common.entity.member.Member;
import yeomeong.common.entity.member.rtype;
import yeomeong.common.repository.MemberRepository;
import yeomeong.common.repository.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    /**
     * 일정 목록보기 ( 연, 월, 일별로
     * 선생님은 해당반과 전체 공지사항 확인 가능
     * 원장님은 모든 반 공지사항 확인 가능, 반 별 확인도 가능
     */


    //전체 공지용 스케줄 목록 가져오기
    public List<ScheduleByDayListDto> getScheduleByAllNoticeAndDayList(Long kindergartenId, LocalDate localDate){

        List<Schedule> allByKindergartenIdAndEventDate = scheduleRepository.findAllByBan_Kindergarten_IdAndEventDateAndScheduleTypeOrderByEventTimeDesc(kindergartenId, localDate, Schedule.ScheduleType.ALLNOTICE);

        return getScheduleByDayListDtos(allByKindergartenIdAndEventDate);
    }


    //반 별 스케줄 가져오기
    public List<ScheduleByDayListDto> getScheduleByBanAndDayList(Long banId, LocalDate localdate) {

        List<Schedule> allByBanIdAndEventDateAndScheduleType = scheduleRepository.findAllByBan_IdAndEventDateAndScheduleTypeOrderByEventTimeDesc(banId, localdate, Schedule.ScheduleType.FORBAN);

        return getScheduleByDayListDtos(allByBanIdAndEventDateAndScheduleType);

    }

    private List<ScheduleByDayListDto> getScheduleByDayListDtos(List<Schedule> scheduleList) {
        List<ScheduleByDayListDto> scheduleByDayList = new ArrayList<>();

        for(Schedule schedule : scheduleList){

            ScheduleByDayListDto scheduleByDay = new ScheduleByDayListDto(
                    schedule.getId(),
                    schedule.getKeyword(),
                    schedule.getContent(),
                    schedule.getScheduleType()
            );

            scheduleByDayList.add(scheduleByDay);
        }

        return scheduleByDayList;
    }

    //스케줄 생성하기
    @Transactional
    public void createSchedule(Long memberId, CreateScheduleRequestDto createScheduleDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 멤버가 없습니다"));

        Schedule schedule = new Schedule(
                member.getBan(),
                createScheduleDto.getKeyword(),
                createScheduleDto.getContent(),
                createScheduleDto.getLocalDate(),
                LocalTime.now()
        );

        if (member.getRole() == rtype.ROLE_DIRECTOR) { //원장이면

            schedule.setScheduleType(Schedule.ScheduleType.ALLNOTICE);

        } else if(member.getRole() == rtype.ROLE_TEACHER){

            schedule.setScheduleType(Schedule.ScheduleType.FORBAN);
        }

        scheduleRepository.save(schedule);
    }

    //스케줄 수정하기
    @Transactional
    public CreateScheduleResponseDto updateSchedule (Long scheduleId, CreateScheduleRequestDto createScheduleRequestDto){

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 스케줄을 찾을 수 없습니다."));

        schedule.setKeyword(createScheduleRequestDto.getKeyword());
        schedule.setContent(createScheduleRequestDto.getContent());
        schedule.setEventDate(createScheduleRequestDto.getLocalDate());
        schedule.setEventTime(LocalTime.now());

        return new CreateScheduleResponseDto(schedule.getKeyword(),schedule.getContent(),schedule.getEventDate(),schedule.getEventTime());
    }

    //스케줄 삭제하기
    @Transactional
    public void removeSchedule(Long scheduleId){

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 스케줄을 찾을 수 없습니다."));

        scheduleRepository.delete(schedule);
    }

}
