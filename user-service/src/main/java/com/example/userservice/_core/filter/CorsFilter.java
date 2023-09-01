package com.example.userservice._core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("CORS 필터 작동");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

        String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, DELETE, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Api-Key, X-Requested-With, Content-Type, Accept, Authorization");
		chain.doFilter(request, response);
	}
}
