package yeomeong.common.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.client.RestTemplate;
import yeomeong.common.dto.Message;
import yeomeong.common.dto.OpenAiRequestDto;
import yeomeong.common.dto.OpenAiResponseDto;
import yeomeong.common.exception.CustomException;
import yeomeong.common.exception.ErrorCode;

@Service
public class OpenAiService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value(("${ai.openai.url"))
    private String apiUrl;

    private final RestTemplate restTemplate;

    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public String generateText(String role, String prompt) {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 바디 설정
        OpenAiRequestDto request = new OpenAiRequestDto();
        request.setModel("gpt-3.5-turbo");
        request.setMessages(Arrays.asList(
            new Message(role, "당신은"),
            new Message(role, prompt),
            new Message(role, "너가 생성해준 알림장은")
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
}
