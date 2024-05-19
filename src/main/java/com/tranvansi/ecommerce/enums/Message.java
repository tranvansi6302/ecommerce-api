package com.tranvansi.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Message {
    REGISTER_SUCCESS("Đăng ký tài khoản thành công!"),
    LOGIN_SUCCESS("Đăng nhập thành công!"),
    FORGOT_PASSWORD_SUCCESS("Một email đã được gửi đến hòm thư của bạn"),
    RESET_PASSWORD_SUCCESS("Đặt lại mật khẩu thành công"),
    UPDATE_PROFILE_SUCCESS("Cập nhật thông tin thành công"),
    UPLOAD_AVATAR_SUCCESS("Upload avatar thành công"),
    CREATE_ADDRESS_SUCCESS("Thêm địa chỉ thành công"),
    UPDATE_ADDRESS_DEFAULT_SUCCESS("Cập nhật địa chỉ mặc định thành công"),
    UPDATE_USER_SUCCESS("Cập nhật người dùng thành công"),
    DELETE_USER_SUCCESS("Xóa người dùng thành công"),
    CREATE_CATEGORY_SUCCESS("Thêm danh mục thành công"),
    UPDATE_CATEGORY_SUCCESS("Cập nhật danh mục thành công"),
    DELETE_CATEGORY_SUCCESS("Xóa danh mục thành công"),
    CREATE_BRAND_SUCCESS("Thêm thương hiệu thành công"),
    UPDATE_BRAND_SUCCESS("Cập nhật thương hiệu thành công"),
    DELETE_BRAND_SUCCESS("Xóa thương hiệu thành công"),
    CREATE_COLOR_SUCCESS("Thêm màu thành công"),
    UPDATE_COLOR_SUCCESS("Cập nhật màu thành công"),
    DELETE_COLOR_SUCCESS("Xóa màu thành công"),
    CREATE_SIZE_SUCCESS("Thêm size thành công"),
    UPDATE_SIZE_SUCCESS("Cập nhật size thành công"),
    DELETE_SIZE_SUCCESS("Xóa size thành công"),
    CREATE_PRODUCT_SUCCESS("Thêm sản phẩm thành công"),
    CREATE_VARIANT_SUCCESS("Thêm biến thể sản phẩm thành công"),
    DELETE_SOFT_PRODUCT_SUCCESS("Sản phẩm đã được chuyển sang thùng rác, tự động xóa sau 30 ngày"),
    RESTORE_PRODUCT_SUCCESS("Khôi phục sản phẩm thành công"),
    UPDATE_PRODUCT_SUCCESS("Cập nhật sản phẩm thành công"),
    UPDATE_VARIANT_SUCCESS("Cập nhật biến thể sản phẩm thành công"),
    ;
    private String message;
}
