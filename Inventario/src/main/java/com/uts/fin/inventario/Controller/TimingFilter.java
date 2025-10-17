package com.uts.fin.inventario.Controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*")
public class TimingFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(TimingFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long t0 = System.currentTimeMillis();
        chain.doFilter(request, response);
        long t1 = System.currentTimeMillis();
        String uri = (request instanceof HttpServletRequest)
                ? ((HttpServletRequest) request).getRequestURI() : "N/A";
        LOG.info(() -> "[Timing] " + uri + " -> " + (t1 - t0) + " ms");
    }
}
