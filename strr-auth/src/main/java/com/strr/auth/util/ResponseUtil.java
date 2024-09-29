package com.strr.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strr.base.model.Result;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 响应工具类
 */
public class ResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void write(HttpServletResponse response, Result<?> result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(result));
        out.flush();
        out.close();
    }

    public static <T> void writeOk(HttpServletResponse response, T data) throws IOException {
        write(response, Result.ok(data));
    }

    public static void writeError(HttpServletResponse response, String msg) throws IOException {
        write(response, Result.error(msg));
    }

    public static void writeError(HttpServletResponse response, Integer code, String msg) throws IOException {
        write(response, Result.error(code, msg));
    }
}
