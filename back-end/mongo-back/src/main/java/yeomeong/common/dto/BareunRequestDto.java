package yeomeong.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BareunRequestDto {

    private Document document;

    @JsonProperty("encoding_type")
    private String encodingType = "UTF8";

    @JsonProperty("custom_domain")
    private String customDomain = "kidwe";

    @Getter
    public static class Document {
        private String content;
        private String language = "ko-KR";
    }

    public BareunRequestDto() {
        this.document = new Document();
    }

    public void setContent(String content) {
        this.document.content = content;
    }
}