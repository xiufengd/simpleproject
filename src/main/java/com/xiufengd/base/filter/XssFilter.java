package com.xiufengd.base.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        if (uri != null) {
            //用于判断访问地址是否需要过滤，是否可能存在风险，
            // 如果存在风险则用XssHttpServletRequestWrapper进行相应的初始化并处理
            //XssHttpServletRequestWrapper中重写的方法会被自动调用
//        if (uri != null && ignoreUri.indexOf(uri) == -1) {
            filterChain.doFilter(new XssHttpServletRequestWrapper(request),servletResponse);
        } else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
