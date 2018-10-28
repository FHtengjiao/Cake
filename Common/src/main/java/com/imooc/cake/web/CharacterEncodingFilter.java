package com.imooc.cake.web;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(
        filterName = "characterFilter", urlPatterns = "*.do",
        initParams = {
                @WebInitParam(name = "encoding", value = "utf-8")
        }
)
public class CharacterEncodingFilter implements Filter {

    private String encoding;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
    }



    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response =(HttpServletResponse) res;

        request.setCharacterEncoding(this.encoding);
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
