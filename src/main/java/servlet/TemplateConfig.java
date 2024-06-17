package servlet;

import jakarta.servlet.http.*;
import org.thymeleaf.*;
import org.thymeleaf.context.*;
import org.thymeleaf.templateresolver.*;

import java.io.*;

public class TemplateConfig {
    private TemplateEngine templateEngine;
    public TemplateConfig(){
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
    }

    private ITemplateResolver templateResolver(){
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCacheable(false);
        return resolver;
    }

    public void process(String templateName, Context context, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        templateEngine.process(templateName, context, response.getWriter());
    }
}
