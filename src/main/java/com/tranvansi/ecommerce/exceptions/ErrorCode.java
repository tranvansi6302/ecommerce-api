package com.tranvansi.ecommerce.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    UNCAUGHT_EXCEPTION(9999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FULL_NAME_REQUIRED(1001, "Họ tên bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_FULL_NAME_MIN_LENGTH(1002, "Họ tên phải từ 4 kí tự trở lên", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_REQUIRED(1003, "Email bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT(1004, "Email không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_REQUIRED(1005, "Mật khẩu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_MIN_LENGTH(1006, "Mật khẩu phải từ 6 kí tự trở lên", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1007, "Khóa validate không hợp lệ", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1008, "Email đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_WHITESPACE(1009, "Mật khẩu không được chứa khoảng trắng", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN_REQUIRED(1010, "Token bắt buộc nhập", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1011, "Không được phép", HttpStatus.UNAUTHORIZED),
    EMAIL_OR_PASSWORD_INCORRECT(1012, "Email hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST);
    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
