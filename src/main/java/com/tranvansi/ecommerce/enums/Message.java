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
    UPLOAD_AVATAR_SUCCESS("Upload avatar thành công");

    private String message;
}
