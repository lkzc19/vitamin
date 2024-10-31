package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public static BizException error() {
        return new BizException("服务器错误", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
