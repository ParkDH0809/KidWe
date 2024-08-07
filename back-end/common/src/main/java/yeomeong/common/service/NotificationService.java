package yeomeong.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yeomeong.common.dto.notification.FCMMessageDto;
import yeomeong.common.dto.notification.NotificationRequestDto;

@Service
public class NotificationService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/kidwe-5e131/messages:send";

    private final ObjectMapper objectMapper;

    public NotificationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public int sendMessageTo(NotificationRequestDto dto) throws IOException {
        String message = makeMessage(dto);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        getAccessToken();
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(message, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        System.out.println(response.getStatusCode());

        return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
    }

    private String makeMessage(NotificationRequestDto dto) throws JsonProcessingException {
        FCMMessageDto fcmMessage = FCMMessageDto.builder()
            .message(FCMMessageDto.Message.builder()
                .token(dto.getToken())
                .notification(FCMMessageDto.Notification.builder()
                    .title(dto.getTitle())
                    .body(dto.getBody())
                    .build()
                ).build()).validateOnly(false).build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    private static String getAccessToken() throws IOException {
        InputStream inputStream = new ClassPathResource("firebase-cloud-message.json").getInputStream();
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: firebase-cloud-message.json");
        }

        GoogleCredentials googleCredentials = GoogleCredentials
            .fromStream(inputStream)
            .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
        googleCredentials.refreshAccessToken();
        AccessToken accessToken = googleCredentials.getAccessToken();
        if (accessToken == null) {
            throw new IOException("Failed to retrieve access token");
        }
        return accessToken.getTokenValue();
    }

}