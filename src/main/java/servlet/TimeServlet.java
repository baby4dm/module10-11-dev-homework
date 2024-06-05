package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.time.*;
import java.time.format.*;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        String timezone = req.getParameter("timezone");
        ZoneId zoneId;
        if (timezone == null || timezone.isEmpty()) {
            zoneId = ZoneId.of("UTC");
        } else {
            timezone = timezone.trim().replace(" ", "+");
            zoneId = ZoneId.of(timezone);
        }
        LocalDateTime time = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        resp.getWriter().write("The current date and time is: " + time.format(formatter));
        resp.getWriter().close();
    }
}
