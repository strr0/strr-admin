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

    public static <T> void writeResult(HttpServletResponse response, T data) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(Result.ok(data)));
        out.flush();
        out.close();
    }
}
