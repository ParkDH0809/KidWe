package yeomeong.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class OpenAiResponseDto {
    private String id;
    private String object;
    private Long created;
    private String model;
    private Usage usage;
    @JsonProperty("choices")
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    @Getter
    public static class Usage {
        private Long promptToekns;
        private Long completionTokens;
        private Long totalTokens;
    }

    @Getter
    public static class Choice {
        private Message message;
        private String logprobs;
        private String finishReason;
        private Long index;
    }
}
