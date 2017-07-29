package com.qg.newsapp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by K Lin on 2017/7/28.
 */

    @WebFilter(filterName = "/UniformEncodingFilter", urlPatterns = { "/*" })
    public class UniformEncodingFilter implements Filter {

        public void destroy() {

        }

        public void doFilter(ServletRequest request, ServletResponse response,
                             FilterChain chain) throws IOException, ServletException {

            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            chain.doFilter(request, response);
        }

        public void init(FilterConfig fConfig) throws ServletException {

        }
    }

