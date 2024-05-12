package com.tranvansi.ecommerce.exceptions;

import com.tranvansi.ecommerce.enums.ErrorCode;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
}
