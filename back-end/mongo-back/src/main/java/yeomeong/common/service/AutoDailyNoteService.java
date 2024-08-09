package yeomeong.common.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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
import yeomeong.common.dto.AutoDailyNoteRequestDto;
import yeomeong.common.dto.AutoDailyNoteRequestDto.ScheduleInfo;
import yeomeong.common.dto.MemoResponseDto;
import yeomeong.common.dto.Message;
import yeomeong.common.dto.OpenAiRequestDto;
import yeomeong.common.dto.OpenAiResponseDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;
import yeomeong.common.repository.MemoRepository;

@Service
@RequiredArgsConstructor
public class AutoDailyNoteService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;
    @Value(("${ai.openai.url"))
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final MemoRepository memoRepository;

    private String getOpenAI(String role, String prePrompt, String corePrompt, String postPrompt){
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 바디 설정
        OpenAiRequestDto request = new OpenAiRequestDto();
        request.setModel("gpt-3.5-turbo");
        request.setMessages(Arrays.asList(
            new Message(role, prePrompt),
            new Message(role, corePrompt),
            new Message(role, postPrompt)
        ));
        request.setMax_tokens(1000);
        request.setTemperature(0.7);

        HttpEntity<OpenAiRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OpenAiResponseDto> response = restTemplate.exchange(
            apiUrl, HttpMethod.POST, entity, OpenAiResponseDto.class);
        // 응답이 있으면
        if (response.getBody() != null && !response.getBody().getChoices().isEmpty()) {
            return response.getBody().getChoices().get(0).getMessage().getContent();
        }
        // 응답이 없으면
        else {
            throw new CustomException(ErrorCode.NOT_RESPONSE);
        }
    }

    @Transactional
    public String getAutoDailyNote(AutoDailyNoteRequestDto autoDailyNoteRequestDto){
        Long teacherId = autoDailyNoteRequestDto.getTeacherId();
        Long kidId = autoDailyNoteRequestDto.getKidId();
        // 메모 추출하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(formatter);
        List<Memo> memos = memoRepository.findByTeacherIdAndKidIdAndDate(teacherId,
            kidId,
            today
        );
        // 메모에서 정보를 추출하고, 해당 메모를 바탕으로 알림장을 생성하는 로직
        String role = "user";
        String prePrompt = String.format("나는 현재 %s 유치원에서 %s반을 담당하고 있는 %s 유치원 선생님이야."
                + "나는 지금 우리반 아이 %s의 학부모에게 알림장을 작성하고자 해."
                + "내가 오늘의 일정들과 작성해 놓은 메모가 있다면 이를 바탕으로 알림장을 대신 작성해줘."
                + "형식은 '%s : %s 부모님에게' 로 시작하는 알림장을 작성해줘."
                + "원생의 부모님에게 작성하는 알림장이기 때문에 공손한 말투로 정성스럽게 작성해줘.",
            autoDailyNoteRequestDto.getKindergartenName(),
            autoDailyNoteRequestDto.getBanName(),
            autoDailyNoteRequestDto.getTeacherName(),
            autoDailyNoteRequestDto.getKidName(),
            today,
            autoDailyNoteRequestDto.getKidName()
        );
        StringBuilder tmpPrompt = new StringBuilder();
        tmpPrompt.append("[" + autoDailyNoteRequestDto.getBanName() + "의 오늘 하루 일정]\n");
        for(ScheduleInfo scheduleInfo : autoDailyNoteRequestDto.getSchedules()){
            tmpPrompt.append("일정 타입 : " + scheduleInfo.getContent() + " | 일정 이름 : " + scheduleInfo.getKeyword() + "\n");
        }
        tmpPrompt.append("=========================================\n");
        int count = 1;
        for(Memo memo : memos){
            tmpPrompt.append("[" + count + "번 메모]\n");
            tmpPrompt.append(" " + count + "번 메모에 포함된 태그 내용들 :");
            for(Tag tag : memo.getTags()){
                tmpPrompt.append("  <" + tag.getMorpheme() + "> : " + tag.getContent() + "\n" );
            }
            tmpPrompt.append(" " + count + "번 메모의 상세 내용 :" + memo.getContent() + "\n" );
            count += 1;
        }
        String corePrompt = "일정과 메모내용 알려줄게!\n" + tmpPrompt.toString() +"\n";
        String postPrompt = "알림장을 대신해서 잘 작성해줬지?"
            + "나에게 사족없이 '%s : %s 부모님에게' 로 시작하는 부분부터 너가 생성한 알림장 끝까지 '알림장 내용'만 추출해줘."
            + "나는 너가 주는 데이터를 바로 알림장으로 넣어서 보낼거기 때문에 최대한 알림장 내용만 줘."
            + "대답도 필요없고 바로 작성해줘.";
        return getOpenAI(role, prePrompt, corePrompt, postPrompt);
    }
}
