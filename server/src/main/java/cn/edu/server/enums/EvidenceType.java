package cn.edu.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum EvidenceType {

    HASH(0, "纯hash"),
    FILE(1, "文件"),
    TEXT(2, "文本");

    private final Integer code;
    private final String message;

    public static EvidenceType of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(code + " Not Exist"));
    }
}
