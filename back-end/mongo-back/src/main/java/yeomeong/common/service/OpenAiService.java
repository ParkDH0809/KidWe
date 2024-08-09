package yeomeong.common.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yeomeong.common.dto.Message;
import yeomeong.common.dto.OpenAiRequestDto;
import yeomeong.common.dto.OpenAiResponseDto;

@Service
public class OpenAiService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;

    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateText(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        OpenAiRequestDto request = new OpenAiRequestDto();
        request.setModel("gpt-3.5-turbo");
        request.setMessages(Arrays.asList(
            new Message("system", "You are a helpful assistant."),
            new Message("user", "Tell me a joke.")
        ));
        request.setMax_tokens(1000);
        request.setTemperature(0.7);
        HttpEntity<OpenAiRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OpenAiResponseDto> response = restTemplate.exchange(
            API_URL, HttpMethod.POST, entity, OpenAiResponseDto.class);

        if (response.getBody() != null && !response.getBody().getChoices().isEmpty()) {
            return response.getBody().getChoices().get(0).getMessage().getContent();
        } else {
            return "No response from OpenAI";
        }
    }
}
