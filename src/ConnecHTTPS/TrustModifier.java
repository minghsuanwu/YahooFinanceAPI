package ConnecHTTPS;

import javax.net.ssl.*;
import java.net.URLConnection;
import java.security.cert.*;

/**
 * Created by Cookie on 16/8/9.
 */
public class TrustModifier {
    private static final HostnameVerifier VERIFIER = new AllowHostnameVerifier();
    private static final TrustManager[] MANAGER = new TrustManager[]{new AllowManager()};
    private static final TrustModifier MODIFIER = new TrustModifier("TLS");

    private final SSLSocketFactory factory;

    private TrustModifier(String protocol) {
        try {
            SSLContext ctx = SSLContext.getInstance(protocol);
            ctx.init(null, MANAGER, null);
            factory = ctx.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException("Create Trust Modifier Failed.", e);
        }
    }

    private HttpsURLConnection modify(HttpsURLConnection conn) {
        conn.setSSLSocketFactory(factory);
        conn.setHostnameVerifier(VERIFIER);
        return conn;
    }

    /** Call this method and it will modify connection with trust settings. */
    public static URLConnection trust(URLConnection conn) {
        return (conn instanceof HttpsURLConnection)?
                MODIFIER.modify((HttpsURLConnection) conn) : conn;
    }

    private static final class AllowHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static final class AllowManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
        public X509Certificate[] getAcceptedIssuers() { return null; }
    }
}