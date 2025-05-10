package unibuc.SkillLink.configurations;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import java.io.IOException;

@Slf4j
@Component
public class CustomRequestLoggingInterceptor extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest currentRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse currentResponse = (HttpServletResponse) servletResponse;

        StringBuffer requestURL = currentRequest.getRequestURL();
        log.info("Request URL: {}", requestURL);
        try {
            chain.doFilter(currentRequest, servletResponse);
        } finally {
            int status = currentResponse.getStatus();
            log.info("Response status: {}", status);
        }
    }
}

