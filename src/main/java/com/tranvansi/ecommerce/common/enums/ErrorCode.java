package com.tranvansi.ecommerce.common.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    UNCAUGHT_EXCEPTION(9999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_USER_FULL_NAME_REQUIRED(1001, "Họ tên bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_USER_FULL_NAME_MIN_LENGTH(
            1002, "Họ tên phải từ 4 kí tự trở lên", HttpStatus.BAD_REQUEST),
    INVALID_USER_EMAIL_REQUIRED(1003, "Email bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_USER_EMAIL_FORMAT(1004, "Email không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_USER_PASSWORD_REQUIRED(1005, "Mật khẩu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_USER_PASSWORD_MIN_LENGTH(
            1006, "Mật khẩu phải từ 6 kí tự trở lên", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1007, "Khóa validate không hợp lệ", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1008, "Email đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    INVALID_USER_PASSWORD_WHITESPACE(
            1009, "Mật khẩu không được chứa khoảng trắng", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN_REQUIRED(1010, "Token bắt buộc nhập", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1011, "Không được phép", HttpStatus.UNAUTHORIZED),
    EMAIL_OR_PASSWORD_INCORRECT(
            1012, "Email hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1013, "Email không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    INVALID_TOKEN(1014, "Token không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_USER_DATE_OF_BIRTH_FORMAT(
            1015, "Ngày sinh không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_USER_PHONE_NUMBER_FORMAT(
            1016, "Số điện thoại không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_USER_PHONE_NUMBER_REQUIRED(1017, "Số điện thoại bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_USER_DATE_OF_BIRTH_REQUIRED(1018, "Ngày sinh bắt buộc nhập", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1019, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    PHONE_NUMBER_ALREADY_EXISTS(
            1020, "Số điện thoại đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    INVALID_USER_AVATAR_REQUIRED(1021, "Avatar bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_USER_AVATAR_FORMAT(1022, "Avatar không đúng định dạng", HttpStatus.BAD_REQUEST),
    FILE_SIZE_TOO_LARGE(1023, "Dung lượng file quá lớn", HttpStatus.BAD_REQUEST),
    FILE_FORMAT_NOT_SUPPORTED(1024, "Định dạng file không được hỗ trợ", HttpStatus.BAD_REQUEST),
    FORBIDDEN(1025, "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALID_ADDRESS_PROVINCE_REQUIRED(1026, "Tỉnh/Thành phố bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_DISTRICT_REQUIRED(1027, "Quận/Huyện bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_WARD_REQUIRED(1028, "Phường/Xã bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_DESCRIPTION_REQUIRED(
            1029, "Địa chỉ cụ thể bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_IS_DEFAULT_REQUIRED(1030, "Cần chọn địa chỉ mặc định", HttpStatus.BAD_REQUEST),
    INVALID_USER_STATUS_REQUIRED(
            1031, "Trạng thái tài khoản bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ROLES_REQUIRED(1032, "Vai trò bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY_NAME_SIZE(1033, "Tên phải từ 3 đến 50 kí tự", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY_NAME_REQUIRED(1034, "Tên danh mục bắt buộc nhập", HttpStatus.BAD_REQUEST),
    CATEGORY_ALREADY_EXISTS(1035, "Danh mục đã tồn tại", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1036, "Danh mục không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_CATEGORY_SUMMARY_REQUIRED(
            1037, "Mô tả ngắn danh mục bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_BRAND_NAME_REQUIRED(1038, "Tên thương hiệu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_BRAND_SUMMARY_REQUIRED(
            1039, "Mô tả ngắn thương hiệu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    BRAND_ALREADY_EXISTS(1040, "Thương hiệu đã tồn tại", HttpStatus.BAD_REQUEST),
    BRAND_NOT_FOUND(1041, "Thương hiệu không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_COLOR_NAME_REQUIRED(1042, "Tên màu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_COLOR_SIZE(1043, "Tên màu phải từ 3 đến 10 kí tự", HttpStatus.BAD_REQUEST),
    INVALID_COLOR_HEX_REQUIRED(1044, "Mã màu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_COLOR_HEX_SIZE(1045, "Mã màu phải có 7 kí tự", HttpStatus.BAD_REQUEST),
    COLOR_NAME_ALREADY_EXISTS(1046, "Tên màu đã tồn tại", HttpStatus.BAD_REQUEST),
    COLOR_HEX_ALREADY_EXISTS(1047, "Mã màu đã tồn tại", HttpStatus.BAD_REQUEST),
    COLOR_NOT_FOUND(1048, "Màu không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_COLOR_SUMMARY_REQUIRED(1049, "Mô tả ngắn màu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SIZE_NAME_REQUIRED(1050, "Tên size bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SIZE_SUMMARY_REQUIRED(1051, "Mô tả ngắn size bắt buộc nhập", HttpStatus.BAD_REQUEST),
    SIZE_ALREADY_EXISTS(1052, "Size đã tồn tại", HttpStatus.BAD_REQUEST),
    SIZE_NOT_FOUND(1053, "Size không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_SIZE_MINIMUM(1054, "Size phải có 2 ký tự số", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_NAME_REQUIRED(1055, "Tên sản phẩm bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_NAME_LENGTH(
            1056, "Tên sản phẩm phải từ 3 đến 200 ký tự", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY_ID_REQUIRED(1057, "ID danh mục bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_BRAND_ID_REQUIRED(1058, "ID thương hiệu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    PRODUCT_ALREADY_EXISTS(1059, "Sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST),

    INVALID_SIZE_ID_REQUIRED(1060, "ID size bắt buộc nhập", HttpStatus.BAD_REQUEST),

    PRODUCT_NOT_FOUND(1061, "Sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_COLOR_ID_REQUIRED(1062, "ID màu bắt buộc nhập", HttpStatus.BAD_REQUEST),

    INVALID_COLOR_REQUIRED(1063, "Màu bắt buộc nhập", HttpStatus.BAD_REQUEST),

    INVALID_PRODUCT_DESCRIPTION_REQUIRED(
            1064, "Mô tả sản phẩm bắt buộc nhập", HttpStatus.BAD_REQUEST),

    INVALID_PRODUCT_ID_REQUIRED(1065, "ID sản phẩm bắt buộc nhập", HttpStatus.BAD_REQUEST),

    SIZE_OR_COLOR_EXISTS(1066, "Size hoặc màu đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_COLOR_IDS_REQUIRED(1067, "Danh sách id màu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SIZE_IDS_REQUIRED(1068, "Danh sách id size bắt buộc nhập", HttpStatus.BAD_REQUEST),
    SKU_ALREADY_EXISTS(1069, "SKU đã tồn tại", HttpStatus.BAD_REQUEST),

    INVALID_SUPPLIER_NAME_REQUIRED(1070, "Tên nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_TAX_CODE_REQUIRED(1071, "Mã số thuế bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_EMAIL_REQUIRED(1072, "Email nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_EMAIL_FORMAT(1073, "Email nhà cung cấp không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_PHONE_NUMBER_REQUIRED(1074, "Số điện thoại nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_PHONE_NUMBER_FORMAT(1075, "Số điện thoại nhà cung cấp không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_ADDRESS_REQUIRED(1076, "Địa chỉ nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    SUPPLIER_NAME_ALREADY_EXISTS(1077, "Tên nhà cung cấp đã tồn tại", HttpStatus.BAD_REQUEST),
    SUPPLIER_TAX_CODE_ALREADY_EXISTS(1078, "Mã số thuế đã tồn tại", HttpStatus.BAD_REQUEST),
    SUPPLIER_EMAIL_ALREADY_EXISTS(1079, "Email nhà cung cấp đã tồn tại", HttpStatus.BAD_REQUEST),
    SUPPLIER_PHONE_NUMBER_ALREADY_EXISTS(1080, "Số điện thoại nhà cung cấp đã tồn tại", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
