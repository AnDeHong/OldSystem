package com.example.controller;

import com.example.response.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    protected <T> Response<T> ok(T t) {
        return Response.ok(t);
    }

    protected  ResponseEntity<byte[]> ok(byte[] body, MediaType contentType) {
        return Response.ok(body,contentType);
    }
    protected <T> Response<T> ok() {
        return Response.ok();
    }

    protected <T> Response<T> error(T t) {
        return Response.error(t);
    }

    protected <T> Response<T> error() {
        return Response.error();
    }
}
