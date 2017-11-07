package ConnecHTTPS;

import java.io.*;
import java.net.*;

/**
 * Created by Cookie on 16/8/9.
 */
public class SampleMain {

    public static void main(String[] args) throws IOException {
        URLConnection conn = new URL("https://74.125.203.94/").openConnection();

        /** Modify connection */
        TrustModifier.trust(conn);

        String inputLine;
        try(InputStreamReader stream = new InputStreamReader(conn.getInputStream());
            BufferedReader in = new BufferedReader(stream)) {
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        }
    }
}