
package com.example.framework.utils;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class GridGraphQL
{

    private GridGraphQL()
    {
    }

    /**
     * Converts common grid.url values into Grid base URL. Example:
     * http://localhost:4444/wd/hub -> http://localhost:4444
     * http://localhost:4444 -> http://localhost:4444
     */
    public static String toGridBaseUrl(String gridUrl)
    {
        if (gridUrl == null)
            return null;
        String u = gridUrl.trim();

        if (u.endsWith("/wd/hub"))
        {
            u = u.substring(0, u.length() - "/wd/hub".length());
        }
        if (u.endsWith("/"))
        {
            u = u.substring(0, u.length() - 1);
        }
        return u;
    }

    /**
     * Returns nodeUri for the current session, like: http://<node-ip>:<port>
     * Uses Selenium Grid 4 GraphQL: { session(id:"...") { nodeUri } }
     */
    public static String getNodeUri(RemoteWebDriver driver, String gridUrl)
    {
        try
        {
            String base = toGridBaseUrl(gridUrl);
            if (base == null || base.isBlank())
                return "UNKNOWN";

            String sessionId = driver.getSessionId().toString();
            URL url = new URL(base + "/graphql");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            // Selenium Grid GraphQL schema supports session(id) { nodeUri }
            // [1](https://mmitacnz-my.sharepoint.com/personal/84213_365kit_co/Documents/Microsoft%20Copilot%20Chat%20Files/SimpleThreadSafeSeleniumFramework.txt)
            String gql = "{ session (id: \\\"" + sessionId + "\\\") { nodeUri } }";
            String payload = "{\"query\":\"" + gql.replace("\"", "\\\\\"") + "\"}";

            try (OutputStream os = conn.getOutputStream())
            {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)))
            {
                String line;
                while ((line = br.readLine()) != null)
                    sb.append(line);
            }
            conn.disconnect();

            // Tiny parse: find "nodeUri":"http://x.x.x.x:port"
            String json = sb.toString();
            int idx = json.indexOf("nodeUri");
            if (idx == -1)
                return "UNKNOWN";

            int start = json.indexOf("http", idx);
            int end = json.indexOf("\"", start);
            if (start == -1 || end == -1)
                return "UNKNOWN";

            return json.substring(start, end);

        }
        catch (Exception e)
        {
            return "UNKNOWN";
        }
    }

    /**
     * Extracts host/IP from nodeUri Example: http://192.168.1.10:5555 ->
     * 192.168.1.10
     */
    public static String hostFromNodeUri(String nodeUri)
    {
        try
        {
            return URI.create(nodeUri).getHost();
        }
        catch (Exception e)
        {
            return "UNKNOWN";
        }
    }
}
