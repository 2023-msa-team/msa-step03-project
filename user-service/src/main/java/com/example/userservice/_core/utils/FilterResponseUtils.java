package com.example.userservice._core.utils;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.example.userservice._core.errors.exception.Exception401;
import com.example.userservice._core.errors.exception.Exception403;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FilterResponseUtils {
    public static void unAuthorized(HttpServletResponse resp, Exception401 e) throws IOException {
        resp.setStatus(e.status().value());
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        resp.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse resp, Exception403 e) throws IOException {
        resp.setStatus(e.status().value());
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        resp.getWriter().println(responseBody);
    }
}
