package com.example.filter;

import javax.servlet.*;
import java.io.IOException;

public class DemoFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("==>DemoFilter启动");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("==>DemoFilter拦截请求");
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
