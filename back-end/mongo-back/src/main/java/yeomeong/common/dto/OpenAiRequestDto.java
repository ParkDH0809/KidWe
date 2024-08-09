package yeomeong.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class OpenAiRequestDto {
    private String model;
    @JsonProperty("messages")
    private List<Message> messages;
    private int max_tokens;
    private double temperature;

    public void setModel(String model) {
        this.model = model;
    }

    public void setMessages(List<Message> messages){
        this.messages = messages;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}

