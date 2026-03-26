package ru.sibiryaq.filter;

import ru.sibiryaq.exception.InvalidRequestException;
import ru.sibiryaq.exception.MatchNotFoundException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebFilter("/*")
public class ExceptionHandlerFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            chain.doFilter(request, response);
        } catch (MatchNotFoundException e) {
            logger.warn("Match not found: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (InvalidRequestException e) {
            logger.warn("Invalid request: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logger.error("Unhandled exception", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}