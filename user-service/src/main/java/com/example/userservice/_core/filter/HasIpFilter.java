package com.example.userservice._core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.userservice._core.errors.exception.Exception403;
import com.example.userservice._core.utils.FilterResponseUtils;

public class HasIpFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {
    System.out.println("HasIpFilter 필터 작동");
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    String clientIp = request.getRemoteAddr();
    System.out.println("클라이언트 ip : " + clientIp);
    if (!clientIp.equals("192.168.0.20")) {
      FilterResponseUtils.forbidden(response, new Exception403("허용되지 않는 ip : " + clientIp));
      return;
    }
    chain.doFilter(request, response);
  }

}
