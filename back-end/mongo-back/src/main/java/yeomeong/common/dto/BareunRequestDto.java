package yeomeong.common.dto;

import lombok.Getter;

@Getter
public class BareunRequestDto {
    private Document document;
    private final String encodingType = "UTF-8";
    private final String custeomDomain = "kidwe";
    private static class Document{
        private String content;
        private final String language = "ko-KR";
    }

    public void setContent(String content){
        this.document.content = content;
    }
}
