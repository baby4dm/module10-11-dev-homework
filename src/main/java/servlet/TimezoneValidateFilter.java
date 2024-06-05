package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.time.*;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String zone = req.getParameter("timezone");
        if (zone != null && !zone.isEmpty()) {
            zone = zone.trim().replace(" ", "+");
            if (!isValidTimeZone(zone)) {
                res.setContentType("text/html; charset=utf-8");
                res.setStatus(400);
                res.getWriter().write("Invalid time zone");
                res.getWriter().close();
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private boolean isValidTimeZone(String timeZone) {
        try {
            ZoneId.of(timeZone);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
