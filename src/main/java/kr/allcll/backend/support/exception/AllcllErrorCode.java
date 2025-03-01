package kr.allcll.backend.support.exception;

public enum AllcllErrorCode {

    SUBJECT_NOT_FOUND("존재하지 않는 과목 입니다."),

    PIN_LIMIT_EXCEEDED("이미 %d개의 핀을 등록했습니다."),
    DUPLICATE_PIN("%s은(는) 이미 핀 등록된 과목입니다."),
    PIN_SUBJECT_MISMATCH("핀에 등록된 과목이 아닙니다."),

    STAR_LIMIT_EXCEEDED("이미 %d개의 즐겨찾기를 등록했습니다."),
    DUPLICATE_STAR("%s은(는) 이미 핀 등록된 과목입니다."),
    STAR_SUBJECT_MISMATCH("즐겨찾기에 등록된 과목이 아닙니다."),

    TOKEN_NOT_FOUND("쿠키에 토큰이 존재하지 않습니다."),

    BASKET_NOT_FOUND("관심과목 정보가 존재하지 않습니다."),

    EXTERNAL_CONNECTION_TERMINATED("외부 서버와의 연결이 종료되었습니다."),

    SSE_EMITTER_NOT_FOUND("존재하지 않는 SSE 연결입니다"),
    SSE_CONNECTION_DENIED("현재 SSE 연결을 할 수 없습니다."),
    SSE_CONNECTION_ALREADY_OPEN("SSE가 이미 연결되어 있습니다."),
    ;

    private String message;

    AllcllErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
