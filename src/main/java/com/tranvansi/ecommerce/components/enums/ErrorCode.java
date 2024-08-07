package com.tranvansi.ecommerce.components.enums;

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
    INVALID_COLOR_STRING_REQUIRED(1067, "Danh sách màu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SIZE_STRING_REQUIRED(
            1068, "Danh sách kích thước bắt buộc nhập", HttpStatus.BAD_REQUEST),
    SKU_ALREADY_EXISTS(1069, "SKU đã tồn tại", HttpStatus.BAD_REQUEST),

    INVALID_SUPPLIER_NAME_REQUIRED(1070, "Tên nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_TAX_CODE_REQUIRED(1071, "Mã số thuế bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_EMAIL_REQUIRED(
            1072, "Email nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_EMAIL_FORMAT(
            1073, "Email nhà cung cấp không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_PHONE_NUMBER_REQUIRED(
            1074, "Số điện thoại nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_PHONE_NUMBER_FORMAT(
            1075, "Số điện thoại nhà cung cấp không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_ADDRESS_REQUIRED(
            1076, "Địa chỉ nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    SUPPLIER_NAME_ALREADY_EXISTS(1077, "Tên nhà cung cấp đã tồn tại", HttpStatus.BAD_REQUEST),
    SUPPLIER_TAX_CODE_ALREADY_EXISTS(1078, "Mã số thuế đã tồn tại", HttpStatus.BAD_REQUEST),
    SUPPLIER_EMAIL_ALREADY_EXISTS(1079, "Email nhà cung cấp đã tồn tại", HttpStatus.BAD_REQUEST),
    SUPPLIER_PHONE_NUMBER_ALREADY_EXISTS(
            1080, "Số điện thoại nhà cung cấp đã tồn tại", HttpStatus.BAD_REQUEST),
    SUPPLIER_NOT_FOUND(1081, "Nhà cung cấp không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_SUPPLIER_STATUS_REQUIRED(
            1082, "Trạng thái nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    VARIANT_NOT_FOUND(1083, "Biến thể không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_PURCHASE_SUPPLIER_ID_REQUIRED(
            1084, "ID nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PURCHASE_ORDER_CODE_REQUIRED(1085, "Mã đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PURCHASE_DETAILS_REQUIRED(
            1086, "Danh sách sản phẩm đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PURCHASE_DETAIL_QUANTITY_REQUIRED(
            1087, "Số lượng sản phẩm đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PURCHASE_DETAIL_VARIANT_ID_REQUIRED(
            1088, "ID biến thể sản phẩm đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PURCHASE_DETAIL_PURCHASE_PRICE_REQUIRED(
            1089, "Giá mua sản phẩm đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    PURCHASE_ORDER_CODE_ALREADY_EXISTS(1090, "Mã đơn hàng đã tồn tại", HttpStatus.BAD_REQUEST),
    PURCHASE_ORDER_NOT_FOUND(1091, "Đơn hàng không tồn tại", HttpStatus.NOT_FOUND),
    PURCHASE_DETAIL_NOT_FOUND(1092, "Chi tiết đơn hàng không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_QUANTITY_RECEIVED_REQUIRED(
            1093, "Số lượng nhận sản phẩm đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    QUANTITY_RECEIVED_GREATER_THAN_PURCHASED(
            1094, "Số lượng nhận lớn hơn số lượng mua", HttpStatus.BAD_REQUEST),
    INVALID_PURCHASE_ORDER_STATUS_REQUIRED(
            1095, "Trạng thái đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    WAREHOUSE_VARIANT_NOT_FOUND(1096, "Biến thể không tồn tại trong kho", HttpStatus.BAD_REQUEST),
    PRICE_PLAN_START_DATE_INVALID(1097, "Ngày bắt đầu không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_ID_REQUIRED(1098, "ID biến thể bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PRICE_PLAN_START_DATE_REQUIRED(
            1099, "Ngày bắt đầu giá bán bắt buộc nhập", HttpStatus.BAD_REQUEST),
    PRICE_PLAN_NOT_FOUND(1100, "Không tìm thấy giá bán", HttpStatus.NOT_FOUND),
    INVALID_PRICE_PLAN_REQUIRED(1101, "Giá bán bắt buộc nhập", HttpStatus.BAD_REQUEST),
    PRODUCT_ALREADY_IN_CART(1102, "Sản phẩm đã tồn tại trong giỏ hàng", HttpStatus.BAD_REQUEST),
    INVALID_CART_QUANTITY(1103, "Số lượng sản phẩm bắt buộc nhập", HttpStatus.BAD_REQUEST),
    CART_DETAIL_NOT_FOUND(
            1104, "Không tìm thấy sản phẩm trong chi tiết giỏ hàng", HttpStatus.NOT_FOUND),
    CART_NOT_FOUND(1105, "Không tìm thấy giỏ hàng", HttpStatus.NOT_FOUND),
    INVALID_ORDER_DETAILS(
            1106, "Danh sách sản phẩm đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_STATUS(1107, "Trạng thái đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1108, "Đơn hàng không tồn tại", HttpStatus.NOT_FOUND),
    ORDER_CANNOT_BE_CANCELLED(1109, "Đơn hàng không thể hủy", HttpStatus.BAD_REQUEST),
    ORDER_NOT_UPDATE(1110, "Đơn hàng không thể cập nhật", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_PHONE_NUMBER_FORMAT(
            1111, "Số điện thoại đặt hàng không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_REVIEW_RATING_REQUIRED(1112, "Đánh giá bắt buộc chọn", HttpStatus.BAD_REQUEST),
    INVALID_REVIEW_COMMENT_REQUIRED(1113, "Bình luận bắt buộc nhập", HttpStatus.BAD_REQUEST),
    REVIEW_ALREADY_EXISTS(1114, "Đã đánh giá sản phẩm này", HttpStatus.BAD_REQUEST),
    INVALID_REVIEW_IMAGE_URL_REQUIRED(
            1115, "URL ảnh đánh giá bắt buộc nhập", HttpStatus.BAD_REQUEST),
    REVIEW_NOT_FOUND(1116, "Đánh giá không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_IMAGE_LIMIT_EXCEEDED(
            1117, "Một sản phẩm chỉ có thể tối đa 5 ảnh", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_URLS_REQUIRED(1118, "Danh sách ảnh bắt buộc nhập", HttpStatus.BAD_REQUEST),
    REVIEW_IMAGE_LIMIT_EXCEEDED(
            1119, "Một đánh giá chỉ có thể tối đa 5 ảnh", HttpStatus.BAD_REQUEST),
    INVALID_REVIEW_IMAGE_REQUIRED(1120, "Ảnh đánh giá bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_REVIEW_IMAGE_FORMAT(1121, "Ảnh đánh giá không đúng định dạng", HttpStatus.BAD_REQUEST),
    INVALID_PURCHASE_DETAIL_QUANTITY(
            1122, "Số lượng sản phẩm đơn hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_PURCHASE_DETAIL_PURCHASE_PRICE(
            1123, "Giá mua sản phẩm đơn hàng không hợp lệ", HttpStatus.BAD_REQUEST),
    PURCHASE_ORDER_NOT_UPDATE(1124, "Đơn hàng không thể cập nhật", HttpStatus.BAD_REQUEST),
    WAREHOUSE_NOT_FOUND(1125, "Kho không tồn tại", HttpStatus.NOT_FOUND),
    PASSWORD_CURRENT_INCORRECT(1126, "Mật khẩu hiện tại không chính xác", HttpStatus.BAD_REQUEST),
    INVALID_USER_CURRENT_PASSWORD_REQUIRED(
            1127, "Mật khẩu hiện tại bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_USER_NEW_PASSWORD_REQUIRED(1128, "Mật khẩu mới bắt buộc nhập", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(1129, "Địa chỉ không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_ADDRESS_ID_REQUIRED(1130, "ID địa chỉ bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_PROVINCE_ID_REQUIRED(
            1131, "ID tỉnh/thành phố bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_DISTRICT_ID_REQUIRED(
            1132, "ID quận/huyện bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS_WARD_ID_REQUIRED(1133, "ID phường/xã bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_USER_IDS_REQUIRED(1134, "ID người dùng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_CATEGORY_IDS_REQUIRED(1135, "ID danh mục bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_BRAND_IDS_REQUIRED(1136, "ID thương hiệu bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_IDS_REQUIRED(1137, "ID sản phẩm bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SUPPLIER_IDS_REQUIRED(1138, "ID nhà cung cấp bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PROMOTION_PRICE_REQUIRED(1139, "Giá khuyến mãi bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SALE_PRICE_REQUIRED(1140, "Giá bán bắt buộc nhập", HttpStatus.BAD_REQUEST),
    PROMOTION_PRICE_GREATER_THAN_SALE_PRICE(
            1141, "Giá khuyến mãi phải nhỏ hơn giá bán", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_SKU_REQUIRED(1142, "SKU sản phẩm bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_NAME_REQUIRED(1143, "Tên biến thể bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_SKU_REQUIRED(1144, "SKU biến thể bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_SIZE_REQUIRED(1145, "Size bắt buộc nhập", HttpStatus.BAD_REQUEST),

    VARIANT_SKU_ALREADY_EXISTS(1146, "SKU biến thể đã tồn tại", HttpStatus.BAD_REQUEST),
    VARIANT_NAME_ALREADY_EXISTS(1147, "Tên biến thể đã tồn tại", HttpStatus.BAD_REQUEST),
    VARIANT_COLOR_SIZE_ALREADY_EXISTS(
            1148, "Màu và size biến thể đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_VARIANT_IDS_REQUIRED(1149, "ID biến thể bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_CODE_REQUIRED(1150, "Mã đơn hàng bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT_REQUIRED(1151, "Số tiền thanh toán bắt buộc nhập", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_IMAGES_IDS_REQUIRED(1152, "Id hình ảnh của sản phẩm bắt buộc nhập", HttpStatus.BAD_REQUEST),
    USER_BLOCKED(1153,"Tài khoản đã bị khóa vui lòng liên hệ quản trị để được hổ trợ",HttpStatus.BAD_REQUEST),
    INVALID_USER_ID_REQUIRED(1154,"ID người dùng bắt buộc nhập",HttpStatus.BAD_REQUEST),
    INVALID_ORDER_ID_REQUIRED(1155,"ID đơn hàng bắt buộc nhập",HttpStatus.BAD_REQUEST),

    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
