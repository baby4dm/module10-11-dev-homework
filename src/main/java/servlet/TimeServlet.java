package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.thymeleaf.context.*;

import java.io.*;
import java.time.*;
import java.time.format.*;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateConfig templateConfig = new TemplateConfig();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        String timezone = handleTimezone(req);
        ZoneId zoneId = ZoneId.of(timezone);

        LocalDateTime time = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        context.setVariable("time", time.format(formatter));
        context.setVariable("timeBelt", timezone);
        resp.addCookie(new Cookie("lastTimezone", timezone));
        templateConfig.process("index", context, resp);
    }

    private String handleTimezone(HttpServletRequest request) {
        String timezone = request.getParameter("timezone");
        if ((timezone == null || timezone.isEmpty()) && request.getCookies() == null) {
            timezone = "UTC";
        } else if ((timezone == null || timezone.isEmpty()) && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("lastTimezone".equals(cookie.getName())) {
                    timezone = cookie.getValue();
                    break;
                }
            }
        } else {
            timezone = timezone.trim().replace(" ", "+");
        }
        return timezone;
    }
}
