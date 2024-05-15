package com.tranvansi.ecommerce.enums;

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
    EMAIL_OR_PASSWORD_INCORRECT(1012, "Email hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1013, "Email không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    INVALID_TOKEN(1014, "Token không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_DATE_OF_BIRTH_FORMAT(1015, "Ngày sinh không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER_FORMAT(1016, "Số điện thoại không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER_REQUIRED(1017, "Số điện thoại bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_DATE_OF_BIRTH_REQUIRED(1018, "Ngày sinh bắt buộc nhập", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1019, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    PHONE_NUMBER_ALREADY_EXISTS(1020, "Số điện thoại đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    INVALID_AVATAR_REQUIRED(1021, "Avatar bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_AVATAR_FORMAT(1022, "Avatar không đúng định dạng", HttpStatus.BAD_REQUEST),
    FILE_SIZE_TOO_LARGE(1023, "Dung lượng file quá lớn", HttpStatus.BAD_REQUEST),
    FILE_FORMAT_NOT_SUPPORTED(1024, "Định dạng file không được hỗ trợ", HttpStatus.BAD_REQUEST),
    FORBIDDEN(1025, "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALID_PROVINCE_REQUIRED(1026, "Tỉnh/Thành phố bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_DISTRICT_REQUIRED(1027, "Quận/Huyện bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_WARD_REQUIRED(1028, "Phường/Xã bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_DESCRIPTION_REQUIRED(1029, "Địa chỉ cụ thể bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_IS_DEFAULT_REQUIRED(1030, "Cần chọn địa chỉ mặc định", HttpStatus.BAD_REQUEST),
    INVALID_BLOCKED_REQUIRED(1031, "Trạng thái blocked bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ROLES_REQUIRED(1032, "Vai trò bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_NAME_SIZE (1033,"Tên phải từ 3 đến 50 kí tự", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY_NAME_REQUIRED(1034,"Tên danh mục bắt buộc nhập", HttpStatus.BAD_REQUEST),
    CATEGORY_ALREADY_EXISTS(1035, "Danh mục đã tồn tại", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1036, "Danh mục không tồn tại", HttpStatus.NOT_FOUND),;


    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
