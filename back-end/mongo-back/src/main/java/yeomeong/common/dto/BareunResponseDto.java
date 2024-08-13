package yeomeong.common.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BareunResponseDto {
    private List<Sentence> sentences;
    private String language;

    @Getter
    public static class Sentence {
        private Text text;
        private List<Token> tokens;
        private String refined;
    }

    @Getter
    public static class Text {
        private String content;
        private int beginOffset;
        private int length;
    }
    @Getter
    public static class Token {
        private Text text;
        private List<Morpheme> morphemes;
        private String lemma;
        private String tagged;
        private String modified;

        @Getter
        public static class Morpheme {
            private Text text;
            private String tag;
            private double probability;
            private String outOfVocab;
        }
    }

}