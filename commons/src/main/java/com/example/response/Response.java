package com.example.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class Response<T> {

    private String code; //成功：10000；失败：E0000

    private String message; //返回消息

    private Object data; //返回体数据

    public static <T> Response<T> ok(T t) {
        return Response.<T>builder().code("10000").data(t).message("success").build();
    }

    public static <T> Response<T> ok() {
        return Response.<T>builder().code("10000").message("success").build();
    }
//    todo  返回图片
    public static ResponseEntity<byte[]> ok(byte[] body, MediaType contentType) {
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(body);
    }
    public static <T> Response<T> error(T t) {
        return Response.<T>builder().code("E0000").data(t).message("error").build();
    }
    public static <T> Response<T> error() {
        return Response.<T>builder().code("E0000").message("error").build();
    }
}
