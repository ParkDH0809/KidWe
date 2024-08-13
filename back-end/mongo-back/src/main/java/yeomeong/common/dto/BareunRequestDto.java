package yeomeong.common.dto;

import com.knuddels.jtokkit.api.EncodingType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class BareunRequestDto {
    private List<String> sentences;
    private String language = "ko-KR";
    private String encodingType = "UTF-8";
    private String customDomain = "kidwe";
    private boolean autoSpacing = false;
    private boolean autoJointing = true;

    public void setContent(String content){
        if(sentences == null){
            sentences = new ArrayList<>();
        }
        else{
            sentences.clear();
            sentences.add(content);
        }
    }
}
