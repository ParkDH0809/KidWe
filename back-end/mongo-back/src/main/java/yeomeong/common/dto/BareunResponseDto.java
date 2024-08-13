package yeomeong.common.dto;

import java.util.List;

public class BareunResponseDto {
    private List<String> sentences; //한 개 이상의 문장으로 구분됩니다.
    private Boolean refined;		//문장의 띄어쓰기, 붙여쓰기 등으로 바뀌었으면 정제되었다고 표시
    private Text text;              //문장의 전체 텍스트 조각
    private class Text{
        String content;             //문장의 내용
        Integer begin_offset;       //문장의 시작 위치
        Long length;                //문장의 길이
    }

    tokens	[]			어절의 배열
    text	{}		어절의 텍스트 조각
    content		어절의 내용
    begin_offset		어절의 시작 위치
    length		어절의 길이
    lemma			원형
    tagged			태깅 형태로 만든 문자열
    modified			붙여쓰기한 경우, 붙여진 결과
    morphemes	[]		형태소의 배열
    text	{}	형태소의 텍스트 조각
    content	형태소의 내용
    begin_offset	형태소의 시작 위치
    length	형태소의 시작 위치
    tag		형태소 태그, 모두 47개의 품사가 사용됩니다.
    probability		형태 분류의 정확도
    out_of_vocab		형태 분류 중 사전 활용의 결과 표시.
    IN_WORD_EMBEDDING: 워드임베딩에 포함된 내용
    OUT_OF_VOCAB: 자동 추측
    IN_CUSTOM_DICT: 사용자 제공 사전에 있는 내용
    IN_BUILTIN_DICT : 기본 사전에 포함된 내용
    language
}
