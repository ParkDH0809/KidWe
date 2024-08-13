package yeomeong.common.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class BareunResponseDto {
    private List<Sentence> sentences;
    @Getter
    public static class Sentence {
        private boolean refined;
        private TextFragment text;
        private List<Token> tokens;
    }

    @Getter
    public static class TextFragment {
        private String content;
        private int beginOffset;
        private int length;
    }
    @Getter
    public static class Token {
        private TextFragment text;
        private String lemma;
        private String tagged;
        private String modified;
        private List<Morpheme> morphemes;
    }
    @Getter
    public static class Morpheme {
        private TextFragment text;
        private String content;
        private int beginOffset;
        private int length;
        private String tag;
        private double probability;
        private String outOfVocab;
    }

    private String language;
}