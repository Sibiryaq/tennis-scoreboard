package ru.sibiryaq;

import org.junit.jupiter.api.Test;
import ru.sibiryaq.exception.InvalidRequestException;
import ru.sibiryaq.exception.MatchNotFoundException;
import ru.sibiryaq.filter.ExceptionHandlerFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class ExceptionHandlerFilterTest {

    private final ExceptionHandlerFilter filter = new ExceptionHandlerFilter();

    @Test
    void shouldReturn404WhenMatchNotFound() throws Exception {
        ServletRequest request = mock(ServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        doThrow(new MatchNotFoundException("not found"))
                .when(chain).doFilter(request, response);

        filter.doFilter(request, response, chain);

        verify(response).sendError(404, "not found");
    }

    @Test
    void shouldReturn400WhenInvalidRequest() throws Exception {
        ServletRequest request = mock(ServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        doThrow(new InvalidRequestException("bad request"))
                .when(chain).doFilter(request, response);

        filter.doFilter(request, response, chain);

        verify(response).sendError(400, "bad request");
    }

    @Test
    void shouldReturn500OnUnknownException() throws Exception {
        ServletRequest request = mock(ServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        doThrow(new RuntimeException("boom"))
                .when(chain).doFilter(request, response);

        filter.doFilter(request, response, chain);

        verify(response).sendError(500, "Internal server error");
    }
}
